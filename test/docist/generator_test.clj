(ns docist.generator-test
  (:require 
    [clojure.test :refer [is testing deftest]]
    
    [docist.generator :as g]
    [docist.fmt :refer [echo PURPLE GENERATOR-TEST NC]]))

(println (str PURPLE GENERATOR-TEST NC))

;; (g/generate {:dir "src/docist" :theme "markdown"})
(g/generate {:dir "src/docist" :theme "html"})

(testing "edn generator"
  )

(testing "json generator"
  )

(testing "yaml generator"
  )

(testing "markdown theme"
  )

(testing "html theme"
  )

(testing "hugo-markdown theme"
  )

(testing "hugo-html theme"
  )

