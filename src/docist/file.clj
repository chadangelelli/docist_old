(ns docist.file
  "Provides file-related utilities"
  {:author "Chad Angelelli"
   :added "0.1"}
  (:require
    [clojure.string :as string]
    [babashka.fs :as fs]
    
    [docist.fmt :as fmt]))

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

(defn find-file
  "Recursively search dir. Return file object of first match for pattern."
  {:author "Chad A."
   :added "0.1"}
  [dir pat]
  (try (-> (fs/match dir (str "regex:" pat)) first fs/file)
       (catch Throwable _ nil)))

(defn directory?
  "Returns true if directory exists and is a directory."
  {:author "Chad A."
   :added "0.1"}
  [dir]
  (fs/directory? dir))

(defn create-dir 
  "Creates directory if it doesn't exist."
  {:author "Chad A."
   :added "0.1"}
  [dir]
  (when-not (directory? dir)
    (fs/create-dir dir)
    nil))

(defn delete-dir
  "Deletes dir if exists. Returns nil or throws."
  {:author "Chad A."
   :added "0.1"}
  [dir]
  (when (directory? dir)
    (fs/delete-tree dir)))

(defn make-theme-dir
  "Returns string for theme directory."
  {:author "Chad A."
   :added "0.1"}
  [theme]
  (str "themes/" (name theme)))

(defn copy-theme
  "Copies all files from theme except Index/Namespace templates."
  {:author "Chad A."
   :added "0.1"}
  [theme output-dir]
  (fs/copy-tree (make-theme-dir theme) output-dir))
