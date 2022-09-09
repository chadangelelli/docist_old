(ns docist.generator.markdown 
  "Generate Github-flavored Markdown."
  {:author "Chad Angelelli"
   :added "0.1"}
  (:require 
    [selmer.parser :as selmer]
    [docist.fmt :refer [echo]]))

(defn generate
  "Write markdown to disk"
  {:author "Chad A." 
   :added "0.1"}
  [parsed options]
  (let [[tpl-idx tpl-ns tpl-sym
         ] (map #(slurp (str "resources/formats/markdown/" % ".md"))
                ["index" "namespace" "symbol"])
        
        idx (selmer/render tpl-idx {:_ parsed})]

    idx

    )
  )
