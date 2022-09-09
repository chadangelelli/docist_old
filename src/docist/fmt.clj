(ns docist.fmt
  "Formatting tools and vars.

  - Logo Ascii art generated using https://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20
  - Test Ascii art generated using https://patorjk.com/software/taag/#p=display&f=Rectangles&t=Parser%20Test 
  "
  (:require 
    [clojure.string :as string]))

(def DOCIST-LOGO "
________                .__          __
\\______ \\   ____   ____ |__| _______/  |_
 |    |  \\ /  _ \\_/ ___\\|  |/  ___/\\   __\\
 |    `   (  <_> )  \\___|  |\\___ \\  |  |
/_______  /\\____/ \\___  >__/____  > |__|
        \\/            \\/        \\/ 
")

(def PARSER-TEST "
 _____                        _____         _   
|  _  |___ ___ ___ ___ ___   |_   _|___ ___| |_ 
|   __| .'|  _|_ -| -_|  _|    | | | -_|_ -|  _|
|__|  |__,|_| |___|___|_|      |_| |___|___|_|  
")

(def FILE-TEST "
 _____ _ _        _____         _   
|   __|_| |___   |_   _|___ ___| |_ 
|   __| | | -_|    | | | -_|_ -|  _|
|__|  |_|_|___|    |_| |___|___|_|  
")

(def GENERATOR-TEST "
 _____                     _              _____         _   
|   __|___ ___ ___ ___ ___| |_ ___ ___   |_   _|___ ___| |_ 
|  |  | -_|   | -_|  _| .'|  _| . |  _|    | | | -_|_ -|  _|
|_____|___|_|_|___|_| |__,|_| |___|_|      |_| |___|___|_|  
")

(def FORMAT-TEST "
 _____                   _      _____         _   
|   __|___ ___ _____ ___| |_   |_   _|___ ___| |_ 
|   __| . |  _|     | .'|  _|    | | | -_|_ -|  _|
|__|  |___|_| |_|_|_|__,|_|      |_| |___|___|_|  
")

(def CMDLINE-TEST "
 _____         _ _ _            _____         _   
|     |_____ _| | |_|___ ___   |_   _|___ ___| |_ 
|   --|     | . | | |   | -_|    | | | -_|_ -|  _|
|_____|_|_|_|___|_|_|_|_|___|    |_| |___|___|_|  
")

(def UTIL-TEST "
 _____ _   _ _    _____         _   
|  |  | |_|_| |  |_   _|___ ___| |_ 
|  |  |  _| | |    | | | -_|_ -|  _|
|_____|_| |_|_|    |_| |___|___|_|  
")

(def NC "\033[0m")
(def BOLD "\033[1m")
(def ITAL "\033[3m")
(def RED "\033[0;31m")
(def BLUE "\033[0;34m")
(def GREEN "\033[0;32m")
(def PURPLE "\033[0;35m")
(def ORANGE "\033[0;33m")

(def SUCCESS (str GREEN  "[SUCCESS]" NC))
(def INFO    (str BLUE   "[INFO]"    NC))
(def WARN    (str ORANGE "[WARN]"    NC))
(def ERROR   (str RED    "[ERROR]"   NC))
(def DEBUG   (str PURPLE "[DEBUG]"   NC))

(defn echo
  [typ & x]
  (let [prefix (case typ
                 :success SUCCESS 
                 :info INFO 
                 :warn WARN 
                 :error ERROR 
                 :debug DEBUG 
                 (str NC))]
    (println (str prefix " " (string/join " " x)))))

(defn pretty-errors
  [errs]
  (let [out (map-indexed (fn [idx item] (str (inc idx) ". " item)) errs)]
    (str "\n\t" (string/join "\n\t" out))))

(defn spaces 
  [n]
  (apply str (repeat n " ")))

(defn make-options-help
  [options]
  (let [[max-a max-b max-c
         ] (loop [rows options, ma 0, mb 0, mc 0]
             (if (empty? rows)
               [ma mb mc]
               (let [[a b c] (first rows)]
                 (recur (rest rows)
                        (max (count a) ma)
                        (max (count b) mb)
                        (max (count c) mc)))))
        rows (map (fn [[a b c]]
                    (let [pad-a (spaces (- max-a (count a)))
                          pad-b (spaces (- max-b (count b)))
                          pad-c (spaces (- max-c (count c)))]
                      (str
                        (spaces 4) 
                        BOLD a pad-a NC (spaces 1)
                        BOLD b pad-b NC (spaces 3)
                        c pad-c)
                      ))
                  options)]
    (string/join "\n" rows)))

(defn make-help-menu
  [options template]
  (-> template
      (string/replace "{{BOLD}}" BOLD)
      (string/replace "{{ITAL}}" ITAL)
      (string/replace "{{NC}}" NC)
      (string/replace "{{RED}}" RED)
      (string/replace "{{BLUE}}" BLUE)
      (string/replace "{{PURPLE}}" PURPLE)
      (string/replace "{{ORANGE}}" ORANGE)
      (string/replace "{{GREEN}}" GREEN)
      (string/replace "{{options}}" (make-options-help options))))
