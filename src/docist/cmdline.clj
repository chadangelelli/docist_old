(ns docist.cmdline
  (:require
    [clojure.tools.cli :as cli]

    [docist.parser :as parser]
    [docist.file :as file]
    [docist.generator :as generator]
    [docist.fmt :as fmt :refer [echo BOLD NC]]))

  ;["-p" "--path PATH" "Filepath to parse"
  ; :default []
  ; :update-fn conj
  ; ]

(def cli-options
  [["-H" "--print-help"]
   ;["-c" "--config FILE" "Provide an EDN config file"]
   ["-d" "--dir DIR" "Directory to process"]
   ["-f" "--file FILE" "File to process"]
   ["-l" "--clj" "Include CLJ, CLJC. Defaults to true."]
   ["-j" "--cljs" "Include CLJS, CLJC"]
   ["-e" "--edn" "Include EDN"]
   ["-o" "--output-dir DIR" "Directory for generated docs"]
   ["-m" "--output-format FORMAT" "Format to generate docs"]
   ["-q" "--quiet" "Only print errors"]])

(def help
  (fmt/make-help-menu
    cli-options
"
Overview:
    Command line interface to Docist.

Syntax:
    {{BOLD}}bb -m docist.cmdline CMD [OPTIONS]{{NC}}
    {{BOLD}}bb -m docist.cmdline [OPTIONS] CMD{{NC}}

Options:
{{options}}

Examples:
    $ bb -m docist.cmdline parse --file=src/docist/parser.clj

    $ bb -m docist.cmdline gen \\
        --dir=src/docist \\
        --export-dir=doc/docist/api \\
        --output-format=hugo-markdown
"))

(defn- -make-parse-file-list
  [{:keys [dir file]}]
  (let [files [(when file (file/->file file))]
        files (if-not dir
                files
                (into files (file/get-files {:dir dir})))]
    (filter identity files)))

(defn -main [& _]
  (let [{:keys [options errors]
         [cmd] :arguments
         {:keys [print-help]} :options
         } (cli/parse-opts *command-line-args* cli-options)]

    (cond
      errors
      (fmt/echo :error (fmt/pretty-errors errors))

      (or print-help (= cmd "help"))
      (fmt/echo :info help)

      :else
      (case cmd 
        "parse"     (parser/parse (-make-parse-file-list options) options)
        "get-files" (file/get-files options)
        "gen"       (-> (parser/parse (-make-parse-file-list options) options)
                        (generator/generate options)) 
        
        (fmt/echo :error (str "Unknown command " fmt/BOLD cmd fmt/NC))))))

