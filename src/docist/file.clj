(ns docist.file
  "Provides file-related utilities"
  {:author "Chad Angelelli"
   :added "0.1"}
  (:require
    [clojure.string :as string]
    [babashka.fs :as fs]))

(defn ->file
  "Return clojure.java.io/file object for path.
  
  ```clojure
  (->file \"src/docist/parser.clj\") 
  ```"
  {:author "Chad A."
   :added "0.1"}
  [path]
  (fs/file path))

(defn get-files
  "Returns a lazy sequence of file objects.

  Options:
      :dir   Directory to scan
      :clj   Include .clj files
      :cljs  Include .cljs files
      :edn   Include .edn files

  Rules:
      1. If none of `#{:clj :cljs :edn}` are provided, default is all 3.

  Examples:

  ```clojure
  (get-files {:dir \"src/docist\" :clj true})
  ;;= (#object[java.io.File 0x251addc4 \"src/docist/parser.clj\"] ...)
  ```"
  {:author "Chad A."
   :added "0.1"}
  [{:keys [dir file] :as options}]
  (let [flags (->> (select-keys options [:clj :cljs :edn])
                   (filter second)
                   (map first)
                   (into #{}))
        scanner (if (empty? flags)
                  "**{.clj,.cljs,.cljc,.edn}"
                  (->> flags
                       (map #(str "." (name %)))
                       (string/join ",")
                       (format "**{%s}" )))
        files (map fs/file (fs/glob dir scanner))
        files (if file
                (conj file (fs/file file))
                files)]
    files))

