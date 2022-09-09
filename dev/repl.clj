(ns repl
  (:require
    [clojure.tools.nrepl.server :as nrepl-server]
    [rebel-readline.main :as rebel]
    
    [docist.fmt :as fmt :refer [DOCIST-LOGO PURPLE GREEN BLUE NC]]))

(defn -main []
  (println PURPLE DOCIST-LOGO NC)
  (fmt/echo :info (str "nREPL server started at " 
                       GREEN "localhost" NC ":" BLUE "40000" NC))
  (nrepl-server/start-server :port 40000)
  (rebel/-main)
  (System/exit 0))
