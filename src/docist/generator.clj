(ns docist.generator
  "Generate static documentation from source files.

  ## Output formats:

  1. `:markdown` (default): generate Github-flavored Markdown 
  2. `:html`: generate standard HTML5
  3. `:hugo-markdown`: generate Github-flavored Markdown plus Hugo Front Matter
  4. `:hugo-html`: generate standard HTML5 plus Hugo Front Matter
  5. `:edn`: Write parsed EDN to _`:output-dir`/\\_parsed.edn_.

  ## Defaults

  - `:output-dir`: \"doc\"
  - `:output-format`: \"markdown\""
  
  {:author "Chad Angelelli"
   :added "0.1"}
  
  (:require 
    [docist.file :as file]
    [docist.parser :as parser]
    [docist.generator.markdown :as gen-md]))

(defn validate-options
  "Return nil on success or error map."
  [options]
  (let [checks
        [["Must provide :dir, :file or :config"
          #(every? not [(:dir %) (:file %) (:config %)])]
         ["Must provide at least one of :clj, :cljs or :edn"
          #(every? not [(:clj %) (:cljs %) (:edn %)])]]]
    (loop [checks checks, errs []]
      (if (empty? checks)
        (when (seq errs)
          {:docist/errors errs})
        (let [[err-msg check] (first checks)
              errs (if (check options) (conj errs err-msg) errs)]
          (recur (rest checks)
                 errs))))))

(defn generate
  [{:keys [clj cljs edn output-dir output-format]
    :or {clj false 
         cljs false 
         edn false
         output-dir "doc"
         output-format :markdown}
    :as options}]
  (if-let [err (validate-options options)]
    err
    (let [options* (assoc options
                          :clj clj
                          :cljs cljs
                          :edn edn
                          :output-dir output-dir
                          :output-format output-format)
          files (file/get-files options*)
          parsed (parser/parse files options*)
          generator (case (keyword (name output-format))
                      :markdown       gen-md/generate 
                      ;:html          html/generate 
                      ;:hugo-markdown hmd/generate 
                      ;:hugo-html     hhtml/generate 
                      ;:edn           edn/generate 
                      )
          ]
      (println (generator parsed options)))))
