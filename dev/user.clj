(ns user
  (:require [docist.fmt :as fmt :refer [DOCIST-LOGO PURPLE NC]]))

(defn logo
  []
  (println PURPLE DOCIST-LOGO NC))


