(ns docist.generator
  "Generate static documentation from source files.

  ## Output formats:

  1. `:edn` (default): EDN map
  2. `:json`: JSON string
  3. `:yaml`: YAML string

  > _NOTE_: Output is processed by default via `categorize-parsed`. 
  Provide `:uncategorized?` option to disable.

  ## Defaults

  - `:output-dir`: \"doc\"
  - `:output-format`: `:edn`"

  {:author "Chad Angelelli"
   :added "0.1"}

  (:require
    [cheshire.core :as json]
    [clj-yaml.core :as yaml]
    [selmer.parser :as selmer]
    [selmer.filters :as sf]
    [markdown.core :as md]

    [docist.file :as file]
    [docist.parser :as parser]
    [docist.fmt :as fmt :refer [BOLD NC]]))

(defn categorize-parsed
  "Return categorized map of `{NAMESPACE {:ns {..} :publics [..] :privates []}`.

  Example:

  ```clojure
  (let [parsed (docist.parser/parse [\"src/docist/parser.clj\"])] 
  (docist.generator/categorize-parsed parsed))
  ```"
  {:author "Chad Angelelli"
   :added "0.1"}
  ([parsed] (categorize-parsed (seq parsed) {}))
  ([parsed out]
   (if (empty? parsed)
     out
     (let [[ns-symbol ns-vars] (first parsed)
           categorized (reduce #(if (= (:node-type %2) :ns)
                                  (assoc %1 :ns %2)
                                  (update %1 
                                          (if (:private? %2) :privates :publics)
                                          conj
                                          %2))
                               {:ns {} :publics [] :privates []}
                               ns-vars)]
       (recur (rest parsed)
              (assoc out ns-symbol categorized))))))

(defn format-parsed
  "Return formatted map for parsed data."
  {:author "Chad Angelelli"
   :added "0.1"}
  [parsed {:keys [output-format uncategorized?]}]
  (let [out (if uncategorized? 
              parsed 
              (categorize-parsed parsed))]
    (case (keyword (name output-format))
      :edn out
      :json (json/generate-string out)
      :yaml (yaml/generate-string out))))

(defn validate-options
  "Return nil on success or error map."
  {:author "Chad Angelelli"
   :added "0.1"}
  [options]
  (let [checks
        [["Must provide :dir, :file or :config"
          #(every? not [(:dir %) (:file %) (:config %)])]
         ["Must provide a theme for rendering output format"
          #(not (:theme %))]]]
    (loop [checks checks, errs []]
      (if (empty? checks)
        (when (seq errs)
          {:docist/errors errs})
        (let [[err-msg check] (first checks)
              errs (if (check options) (conj errs err-msg) errs)]
          (recur (rest checks)
                 errs))))))

(defn get-template
  "Return template string for theme/filename."
  {:author "Chad Angelelli"
   :added "0.1"}
  [theme filename]
  (let [nm (name filename)
        theme-dir (file/make-theme-dir theme)]
    (try (slurp (file/find-file theme-dir (str nm "\\..*")))
         (catch Throwable _
           (str "Cannot find template " BOLD theme-dir "/" nm NC)))))

(sf/add-filter! :md-to-html (fn md-to-html [s] (md/md-to-html-string s)))

(defn render
  "Render context to disk using theme. Returns nil or throws."
  {:author "Chad Angelelli"
   :added "0.1"}
  [data {:keys [theme output-dir] :as options}]
  (let [idx-tpl (get-template theme :index)
        ns-tpl (get-template theme :namespace)
        ctx {:_ data :_options options}
        index (selmer/render idx-tpl ctx)
        namespaces (map (fn [[ns-nm spec]] 
                          [ns-nm (selmer/render ns-tpl {:_ spec})]) 
                        (:_ ctx))
        ext (case (keyword theme)
              (:markdown :md :hugo-markdown) "md"
              (:html :hugo-html) "html")]
    (file/delete-dir output-dir)
    (file/create-dir output-dir)
    (file/copy-theme theme output-dir)
    (spit (format "%s/index.%s" output-dir ext) index)
    (doseq [[ns-nm contents] namespaces]
      (spit (format "%s/%s.%s" output-dir ns-nm ext) contents))
    nil))

(defn generate
  [{:keys [clj cljs edn output-dir output-format quiet]
    :or {clj false 
         cljs false 
         edn false
         output-dir "doc"
         output-format :edn}
    :as options}]
  (if-let [err (validate-options options)]
    err
    (let [options (assoc options
                         :clj clj
                         :cljs cljs
                         :edn edn
                         :output-dir output-dir
                         :output-format output-format)
          steps [["Collecting source files" (fn [_] (file/get-files options))]
                 ["Parsing source files" #(parser/parse % options)]
                 ["Formatting data" #(format-parsed % options)]
                 ["Rendering theme" #(render % options)]
                 [(str "Docs generated in " BOLD output-dir NC) (fn [_] nil)]]
          total-steps (count steps)
          max-msg (apply max (map #(-> % first count) steps))]
      (loop [steps steps, step 1, prev-ret nil]
        (if (empty? steps)
          nil
          (let [[msg f] (first steps)]
            (when-not quiet 
              (fmt/print-progress-bar step total-steps max-msg msg))
            (recur (rest steps)
                   (inc step)
                   (f prev-ret))))))))
