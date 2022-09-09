(ns docist.cmdline
  (:require
    [clojure.tools.cli :as cli]

    [docist.parser :as parser]
    [docist.file :as file]
    [docist.generator :as generator]
    [docist.fmt :as fmt :refer [PURPLE NC DOCIST-LOGO]]))

  ;["-p" "--path PATH" "Filepath to parse"
  ; :default []
  ; :update-fn conj
  ; ]

(def cli-options
  [["-H" "--print-help"]
   ;["-c" "--config FILE" "Provide an EDN config file"]
   ["-d" "--dir DIR" "Directory to process"]
   ["-f" "--file FILE" "File to process"]
   ["-l" "--clj" "Include CLJ, CLJC"]
   ["-j" "--cljs" "Include CLJS, CLJC"]
   ["-e" "--edn" "Include EDN"]
   ["-o" "--output-dir DIR" "Directory for generated docs"]
   ["-m" "--output-format FORMAT" "Format for generated docs"]
   ["-q" "--quiet" "Do not print output"]])

(def help
  (fmt/make-help-menu
    cli-options
"
{{ITAL}}Overview:{{NC}}
    Command line interface to Docist.

{{ITAL}}Commands:{{NC}}
    {{BOLD}}help{{NC}}       Print help menu
    {{BOLD}}parse{{NC}}      Parse source files into Docist nodes
    {{BOLD}}get-files{{NC}}  Get sequence of files to parse
    {{BOLD}}gen{{NC}}        Generate docs

{{ITAL}}Syntax:{{NC}}
    {{BOLD}}bb -m docist.cmdline CMD [OPTIONS]{{NC}}
    {{BOLD}}bb -m docist.cmdline [OPTIONS] CMD{{NC}}

{{ITAL}}Options:{{NC}}
{{options}}

{{ITAL}}Examples:{{NC}}
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

    (println (str PURPLE DOCIST-LOGO NC))

    (cond
      errors
      (fmt/echo :error (fmt/pretty-errors errors))

      (or print-help (= cmd "help"))
      (fmt/echo :info help)

      :else
      (case cmd 
        "parse"     (parser/parse (-make-parse-file-list options) options)
        "gen"       (generator/generate options)
        "get-files" (file/get-files options)
        
        (fmt/echo :error (str "Unknown command " fmt/BOLD cmd fmt/NC))))))

