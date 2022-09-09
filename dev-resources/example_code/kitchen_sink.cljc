(ns example-code.kitchen-sink
  "DOCSTRING::example-code.kitchen-sink"
  {:author "AUTHOR::example-code.kitchen-sink"
   :added "ADDED::0.1"}
  (:require [clojure.string :as string]))

(declare fn-declared var-declared)
(defn fn-declared [] (println "fn-declared"))
(def var-declared 123)

(defn fn-public-meta-full
  "DOCSTRING::fn-public-meta-full"
  {:author "AUTHOR::fn-public-meta-full"
   :added "ADDED::0.2"}
  [a b]
  (+ a b))

(defn fn-public-meta-docstring
  "DOCSTRING::fn-public-meta-docstring"
  [c d]
  (- c d))

(defn fn-public-meta-map
  {:author "AUTHOR::fn-public-meta-map"
   :doc "DOCSTRING::fn-public-meta-map"
   :added "ADDED::0.3"}
  [e f g]
  (string/join ", " [e f g]))

(defn fn-public-meta-none
  []
  (println "fn-public-meta-none"))

(defn fn-public-no-args
  []
  nil)

(defn fn-public-multi-arity
  ([h i] (fn-public-multi-arity h i 52))
  ([j k l]
   (+ j k l)))

(let [not-ideal? true]
  (defn fn-public-inline
    "DOCSTRING::fn-public-inline"
    {:author "AUTHOR::fn-public-inline"
     :added "ADDED::0.4"}
    [x y z]
    (when not-ideal? (str x y z))))

#?(:clj (defn fn-public-clj-only
          "DOCSTRING::fn-public-clj-only"
          {:author "AUTHOR::fn-public-clj-only"
           :added "ADDED::0.5"}
          [a b]
          (+ a b)))

#?(:cljs 
   (do (defn fn-public-cljs-only
         "DOCSTRING::fn-public-cljs-only"
         {:author "AUTHOR::fn-public-cljs-only"
          :added "ADDED::0.6"}
         [a b]
         (+ a b))
       nil ; add a form so CLJ Kondo doesn't bark
       ))

(defn- fn-private-meta-full
  "DOCSTRING::fn-private-meta-full"
  {:author "AUTHOR::fn-private-meta-full"
   :added "ADDED::0.7"}
  [a b c]
  (+ a b c))
fn-private-meta-full ; reference to stop CLJ Kondo barking


(defn- fn-private-meta-docstring
  "DOCSTRING::fn-private-meta-docstring"
  [c d]
  (- c d))
fn-private-meta-docstring ; reference to stop CLJ Kondo barking

(defn- fn-private-meta-map
  {:author "AUTHOR::fn-private-meta-map"
   :doc "DOCSTRING::fn-private-meta-map"
   :added "ADDED::0.8"}
  [e f g]
  (string/join ", " [e f g]))
fn-private-meta-map ; reference to stop CLJ Kondo barking

(defn- fn-private-meta-none
  []
  (println "fn-private-meta-none"))
fn-private-meta-none ; reference to stop CLJ Kondo barking

(defn- fn-private-no-args
  []
  nil)
fn-private-no-args ; reference to stop CLJ Kondo barking

(defn- fn-private-multi-arity
  ([h i] (fn-private-multi-arity h i 52))
  ([j k l]
   (+ j k l)))
fn-private-multi-arity ; reference to stop CLJ Kondo barking

(let [not-ideal? true]
  (defn- fn-private-inline
    "DOCSTRING::fn-private-inline"
    {:author "AUTHOR::fn-private-inline"
     :added "ADDED::0.9"}
    [x y z]
    (when not-ideal? (str x y z)))
  fn-private-inline) ; reference to stop CLJ Kondo barking
  

#?(:clj (defn- fn-private-clj-only
          "DOCSTRING::fn-private-clj-only"
          {:author "AUTHOR::fn-private-clj-only"
           :added "ADDED::0.10"}
          [a b]
          (+ a b)))
fn-private-clj-only ; reference to stop CLJ Kondo barking

#?(:cljs 
   (do (defn- fn-private-cljs-only
         "DOCSTRING::fn-private-cljs-only"
         {:author "AUTHOR::fn-private-cljs-only"
          :added "ADDED::0.11"}
         [a b]
         (+ a b))
       fn-private-cljs-only ; reference to stop CLJ Kondo barking
       nil)) ; add a form so CLJ Kondo doesn't bark

(def ^{:author "AUTHOR::var-public-meta-full"
       :added "ADDED::0.12"
       :doc "DOCSTRING::var-public-meta-full"}
  var-public-meta-full
  1)

(def var-public-meta-docstring 
  "DOCSTRING::var-public-meta-docstring"
  2)

(def var-public-meta-none 3)

(def ^{:private true
       :author "AUTHOR::var-private-meta-full"
       :added "ADDED::0.13"
       :doc "DOCSTRING::var-private-meta-full"}
  var-private-meta-full
  1)
var-private-meta-full ; reference to stop CLJ Kondo barking

(def ^{:private true} 
  var-private-meta-docstring 
  "DOCSTRING::var-private-meta-docstring"
  2)
var-private-meta-docstring ; reference to stop CLJ Kondo barking

(def ^:private var-private-meta-none 3)
var-private-meta-none ; reference to stop CLJ Kondo barking
