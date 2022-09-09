(ns docist.parser
  (:require
    [rewrite-clj.zip :as z]
    [rewrite-clj.node :as n]

    [docist.fmt :refer [echo]]))

(def ^{:author "Chad A."
       :added "0.1"}
  collectables
  "Set of forms that will be collected from parsing.

  Collectable forms:

  ```clojure
  #{'declare
  'def
  'defmacro
  'defmulti
  'defmethod
  'defn
  'defn-
  'defonce
  'ns}
  ```"
  #{'declare 'def 'defmacro 'defmulti 'defmethod 'defn 'defn- 'defonce 'ns})

(declare -parse-node)

(defn- -process-meta
  [zloc]
  (case (z/tag zloc)
    :map (z/sexpr zloc)
    :meta (let [zloc-meta (-> zloc z/down)]
            (case (z/tag zloc-meta)
              :map (z/sexpr zloc-meta)
              :token {(z/sexpr zloc-meta) true}))))

(defn- -get-meta
  [zloc]
  (->> zloc 
       z/down
       (iterate z/right)
       (take-while (complement z/end?))
       (filter #(some #{:meta :map} [(z/tag %)]))
       (map -process-meta)
       first))

(defn- -get-docstring
  [zloc]
  (when-let [ds (-> zloc z/down (z/find-next-token #(string? (z/sexpr %))))]
    (z/sexpr ds)))

(defn- -set-docstring
  [metadata ?docstring]
  (if ?docstring
    (assoc metadata :doc ?docstring)
    metadata))

(defn- -parse-ns
  [zloc _]
  (let [ns-symbol 
        (or (-> zloc z/down (z/find-next-tag :token) z/sexpr)
            (-> zloc 
                z/down 
                (z/find-next-tag :meta) 
                z/node 
                n/children 
                last 
                str
                symbol))
        docstring (-get-docstring zloc)
        metadata (-> (-get-meta zloc)
                     (-set-docstring docstring))]
    {:node-type :ns
     :name ns-symbol
     :metadata metadata}))

(defn- -parse-declare
  [zloc _]
  (let [docstring (-get-docstring zloc)
        metadata (-> (-get-meta zloc)
                     (-set-docstring docstring))
        names (->> zloc
                   z/down
                   z/right
                   (iterate z/right)
                   (take-while (complement z/end?))
                   (map z/sexpr)
                   vec)]
    {:node-type :declare
     :names names
     :metadata metadata}))

(defn- -parse-var
  [zloc _]
  (let [var-name (-> zloc z/down z/right z/sexpr)
        docstring (-get-docstring zloc) 
        metadata (-> (-get-meta zloc)
                     (-set-docstring docstring))]
    {:node-type :var
     :name var-name
     :metadata metadata}))

(defn- -parse-fn-public
  [zloc _]
  (let [fn-name (-> zloc z/down z/right z/sexpr)
        docstring (-get-docstring zloc)
        metadata (-> (-get-meta zloc)
                     (-set-docstring docstring))
        signatures (->> zloc
                        z/down
                        (iterate z/right)
                        (take-while (complement z/end?))
                        (filter #(= (z/tag %) :vector))
                        (map z/sexpr))]
    ;;TODO: fix multiple signatures
    {:node-type :fn
     :name fn-name
     :public? true
     :private? false
     :signatures signatures
     :metadata metadata}))

(defn- -parse-fn-private
  [zloc _]
  (-> (-parse-fn-public zloc _)
      (assoc :public? false :private? true)
      (assoc-in [:metadata :private] true)))

(defn- -parse-macro
  [zloc _]
  (let [macro-name (-> zloc z/down z/right z/sexpr)
        docstring (-get-docstring zloc)
        metadata (-> (-get-meta zloc)
                     (-set-docstring docstring))
        signatures (->> zloc
                        z/down
                        (iterate z/right)
                        (take-while (complement z/end?))
                        (filter #(= (z/tag %) :vector))
                        (map z/sexpr))]
    {:node-type :macro
     :name macro-name
     :signatures signatures
     :metadata metadata}))

(defn- -parse-defmulti
  [_ {:keys [quiet]}]
  (when-not quiet
    (echo :debug "[docist.parser/-parse-defmulti] Add or discuss approach")))

(defn- -parse-defmethod
  [_ {:keys [quiet]}]
  (when-not quiet
    (echo :debug "[docist.parser/-parse-defmethod] Add or discuss approach")))

(defn- -parse-defonce
  [_ {:keys [quiet]}]
  (when-not quiet
    (echo :debug "[docist.parser/-parse-defonce] Add or discuss approach")))

(defn- -parse-node
  "Return updated `namespaces` map"
  [zloc options]
  (let [node-type (-> zloc z/down z/sexpr str)
        node-fn (case node-type
                  "declare"   -parse-declare
                  "def"       -parse-var
                  "defmacro"  -parse-macro
                  "defmulti"  -parse-defmulti
                  "defmethod" -parse-defmethod
                  "defn"      -parse-fn-public
                  "defn-"     -parse-fn-private
                  "defonce"   -parse-defonce
                  "ns"        -parse-ns
                  nil)]
    (when node-fn
      (node-fn zloc options))))

(defn- -make-location-map
  "Return map of location info for collected form."
  [location]
  (let [[[sr sc] [er ec]] location]
    {:location
     {:start-row sr
      :start-col sc
      :end-row er
      :end-col ec}}))

(defn- -parse-namespace 
  "Return nodes for collected forms of namespace"
  ([forms options] (-parse-namespace forms [] options))
  ([forms nodes options]
   (if (empty? forms)
     nodes
     (let [{:keys [location string]} (first forms)
           node (merge
                  (-make-location-map location) 
                  (-parse-node (z/of-string string) options))]
       (recur (rest forms)
              (conj nodes node)
              options)))))

(defn- -parse-namespaces
  ([collected options] (-parse-namespaces collected {} options))
  ([collected out options]
   (if (empty? collected)
     out
     (let [forms (first collected)
           nodes (-parse-namespace forms options)
           ns-symbol (->> nodes
                          (filter #(= (:node-type %) :ns))
                          first
                          :name)]
       (recur (rest collected)
              (assoc out ns-symbol nodes) 
              options)))))

(defn parse
  "Parse all collectable forms for namespace.

  See also `collectables`."
  {:author "Chad A."
   :added "0.1"}
  ([paths] (parse paths [] nil))
  ([paths collected options]
   (if (empty? paths)
     (-parse-namespaces collected options)
     (let [path (first paths)
           zloc (z/of-file path {:track-position? true})
           colled (atom [])]
       (-> zloc
           z/up
           (z/postwalk 
             (fn select [zloc]
               (and (= (z/tag zloc) :list)
                    (some collectables [(-> zloc z/down z/sexpr)])))
             (fn visit [zloc]
               (swap! colled conj {:location (z/position-span zloc)
                                   :string (z/string zloc)})
               zloc)))
       (recur (rest paths) 
              (conj collected @colled)
              options)))))
