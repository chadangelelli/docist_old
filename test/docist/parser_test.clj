(ns docist.parser-test
  (:require 
    [clojure.pprint :refer [pprint]]
    [clojure.test :refer [deftest is testing]]
    [docist.parser :as parser]
    [docist.fmt :refer [echo PARSER-TEST PURPLE NC]]))

(println (str PURPLE PARSER-TEST NC))

(testing "example-code.ns1"
  (deftest parse-ns1
    (let [parsed (parser/parse ["dev-resources/example_code/ns1.clj"])
          ns-node (-> (get parsed 'example-code.ns1) first)]
      (is (= (count parsed) 1))
      (is (= (first (keys parsed)) 'example-code.ns1))
      (is (map? ns-node))
      (is (= (-> ns-node :location :start-row) 1))
      (is (= (-> ns-node :location :start-col) 1))
      (is (= (-> ns-node :location :end-row) 9))
      (is (= (-> ns-node :location :end-col) 33))
      (is (= (-> ns-node :metadata :added) "0.1"))
      (is (= (-> ns-node :metadata :author) "Mitch Hedberg"))
      (is (= (-> ns-node :metadata :doc) "example-code.ns1 docstring"))
      (is (= (:node-type ns-node) :ns))
      (is (= (:name ns-node) 'example-code.ns1)))))

(testing "example-code.ns2"
  (deftest parse-ns2
    (let [parsed (parser/parse ["dev-resources/example_code/ns2.clj"])
          ns-node (-> (get parsed 'example-code.ns2) first)]
      (is (= (count parsed) 1))
      (is (= (first (keys parsed)) 'example-code.ns2))
      (is (map? ns-node))
      (is (= (-> ns-node :location :start-row) 1))
      (is (= (-> ns-node :location :start-col) 1))
      (is (= (-> ns-node :location :end-row) 8))
      (is (= (-> ns-node :location :end-col) 33))
      (is (= (-> ns-node :metadata :added) "0.1"))
      (is (= (-> ns-node :metadata :author) "Mitch Hedberg"))
      (is (= (-> ns-node :metadata :doc) "example-code.ns2 docstring"))
      (is (= (:node-type ns-node) :ns))
      (is (= (:name ns-node) 'example-code.ns2)))))

(testing "example-code.kitchen-sink"
  (deftest parse-kitchen-sink
    (let [parsed (parser/parse ["dev-resources/example_code/kitchen_sink.cljc"])
          nodes (get parsed 'example-code.kitchen-sink)
          ns-node (first nodes)
          symbol-nodes (rest nodes)

          [test-declare 
           fn-declared
           var-declared
           fn-public-meta-full
           fn-public-meta-docstring
           fn-public-meta-map
           fn-public-meta-none
           fn-public-no-args
           fn-public-multi-arity
           fn-public-inline
           fn-public-clj-only
           fn-public-cljs-only
           fn-private-meta-full
           fn-private-meta-docstring
           fn-private-meta-map
           fn-private-meta-none
           fn-private-no-args
           fn-private-multi-arity
           fn-private-inline
           fn-private-clj-only
           fn-private-cljs-only
           var-public-meta-full
           var-public-meta-docstring
           var-public-meta-none
           var-private-meta-full
           var-private-meta-docstring
           var-private-meta-none
           ] symbol-nodes]

      (let [x ns-node]
        (is (= (count parsed) 1))
        (is (= (first (keys parsed)) 'example-code.kitchen-sink))
        (is (map? x))
        (is (= (-> x :location :start-row) 1))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 5))
        (is (= (-> x :location :end-col) 42))
        (is (= (-> x :metadata :added) "ADDED::0.1"))
        (is (= (-> x :metadata :author) 
               "AUTHOR::example-code.kitchen-sink"))
        (is (= (-> x :metadata :doc) 
               "DOCSTRING::example-code.kitchen-sink"))
        (is (= (:node-type x) :ns))
        (is (= (:name x) 'example-code.kitchen-sink)))

      (let [x test-declare]
        (is (map? x))
        (is (= (-> x :location :start-row) 7))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 7))
        (is (= (-> x :location :end-col) 35))
        (is (= (:names x) '[fn-declared var-declared]))
        (is (nil? (:metadata x))))

      ;; fn-declared
      (let [x fn-declared]
        (is (map? x))
        (is (= (-> x :location :start-row) 8))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 8))
        (is (= (-> x :location :end-col) 46))
        (is (= (-> x :public?) true))
        (is (= (-> x :private?) false))
        (is (= (:signatures x) '([])))
        (is (nil? (:metadata x)))) 

      ;; var-declared
      (let [x var-declared]
        (is (map? x))
        (is (= (-> x :location :start-row) 9))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 9))
        (is (= (-> x :location :end-col) 23))
        (is (nil? (:metadata x)))) 

      ;; fn-public-meta-full
      (let [x fn-public-meta-full]
        (is (map? x))
        (is (= (-> x :location :start-row) 11))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 16))
        (is (= (-> x :location :end-col) 11))
        (is (= (-> x :public?) true))
        (is (= (-> x :private?) false))
        (is (= (-> x :metadata :added) "ADDED::0.2"))
        (is (= (-> x :metadata :author) "AUTHOR::fn-public-meta-full"))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-public-meta-full"))
        (is (= (:signatures x) '([a b])))) 

      ;; fn-public-meta-docstring
      (let [x fn-public-meta-docstring]
        (is (map? x))
        (is (= (-> x :location :start-row) 18))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 21))
        (is (= (-> x :location :end-col) 11))
        (is (= (-> x :public?) true))
        (is (= (-> x :private?) false))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-public-meta-docstring"))
        (is (= (:signatures x) '([c d])))) 

      ;; fn-public-meta-map
      (let [x fn-public-meta-map]
        (is (map? x))
        (is (= (-> x :location :start-row) 23))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 28))
        (is (= (-> x :location :end-col) 30))
        (is (= (-> x :public?) true))
        (is (= (-> x :private?) false))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-public-meta-map"))
        (is (= (:signatures x) '([e f g])))) 

      ;; fn-public-meta-none
      (let [x fn-public-meta-none]
        (is (map? x))
        (is (= (-> x :location :start-row) 30))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 32))
        (is (= (-> x :location :end-col) 35))
        (is (= (-> x :public?) true))
        (is (= (-> x :private?) false))
        (is (= (:signatures x) '([])))
        (is (nil? (:metadata x)))) 

      ;; fn-public-no-args
      (let [x fn-public-no-args]
        (is (map? x))
        (is (= (-> x :location :start-row) 34))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 36))
        (is (= (-> x :location :end-col) 7))
        (is (= (-> x :public?) true))
        (is (= (-> x :private?) false))
        (is (= (:signatures x) '([])))
        (is (nil? (:metadata x)))) 

      ;; fn-public-multi-arity
      ;;TODO: fix multi-artity parsing
      (let [x fn-public-multi-arity]
        (is (map? x))
        (is (= (-> x :location :start-row) 38))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 41))
        (is (= (-> x :location :end-col) 15))
        (is (= (-> x :public?) true))
        (is (= (-> x :private?) false))
        (is (= (:signatures x) '()))
        (is (nil? (:metadata x)))) 

      ;; fn-public-inline
      (let [x fn-public-inline]
        (is (map? x))
        (is (= (-> x :location :start-row) 44))
        (is (= (-> x :location :start-col) 3))
        (is (= (-> x :location :end-row) 49))
        (is (= (-> x :location :end-col) 35))
        (is (= (-> x :public?) true))
        (is (= (-> x :private?) false))
        (is (= (:signatures x) '([x y z])))
        (is (= (-> x :metadata :added) "ADDED::0.4"))
        (is (= (-> x :metadata :author) "AUTHOR::fn-public-inline"))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-public-inline"))) 

      ;; fn-public-clj-only
      (let [x fn-public-clj-only]
        (is (map? x))
        (is (= (-> x :location :start-row) 51))
        (is (= (-> x :location :start-col) 9))
        (is (= (-> x :location :end-row) 56))
        (is (= (-> x :location :end-col) 19))
        (is (= (-> x :public?) true))
        (is (= (-> x :private?) false))
        (is (= (:signatures x) '([a b])))
        (is (= (-> x :metadata :added) "ADDED::0.5"))
        (is (= (-> x :metadata :author) "AUTHOR::fn-public-clj-only"))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-public-clj-only"))) 

      ;; fn-public-cljs-only
      (let [x fn-public-cljs-only]
        (is (map? x))
        (is (= (-> x :location :start-row) 59))
        (is (= (-> x :location :start-col) 8))
        (is (= (-> x :location :end-row) 64))
        (is (= (-> x :location :end-col) 18))
        (is (= (-> x :public?) true))
        (is (= (-> x :private?) false))
        (is (= (:signatures x) '([a b])))
        (is (= (-> x :metadata :added) "ADDED::0.6"))
        (is (= (-> x :metadata :author) "AUTHOR::fn-public-cljs-only"))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-public-cljs-only"))) 

      ;; fn-private-meta-full
      (let [x fn-private-meta-full]
        (is (map? x))
        (is (= (-> x :location :start-row) 68))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 73))
        (is (= (-> x :location :end-col) 13))
        (is (= (-> x :public?) false))
        (is (= (-> x :private?) true))
        (is (= (-> x :metadata :added) "ADDED::0.7"))
        (is (= (-> x :metadata :author) "AUTHOR::fn-private-meta-full"))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-private-meta-full"))
        (is (= (:signatures x) '([a b c])))) 

      ;; fn-private-meta-docstring
      (let [x fn-private-meta-docstring]
        (is (map? x))
        (is (= (-> x :location :start-row) 77))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 80))
        (is (= (-> x :location :end-col) 11))
        (is (= (-> x :public?) false))
        (is (= (-> x :private?) true))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-private-meta-docstring"))
        (is (= (:signatures x) '([c d])))) 

      ;; fn-private-meta-map
      (let [x fn-private-meta-map]
        (is (map? x))
        (is (= (-> x :location :start-row) 83))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 88))
        (is (= (-> x :location :end-col) 30))
        (is (= (-> x :public?) false))
        (is (= (-> x :private?) true))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-private-meta-map"))
        (is (= (:signatures x) '([e f g])))) 

      ;; fn-private-meta-none
      (let [x fn-private-meta-none]
        (is (map? x))
        (is (= (-> x :location :start-row) 91))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 93))
        (is (= (-> x :location :end-col) 36))
        (is (= (-> x :public?) false))
        (is (= (-> x :private?) true))
        (is (= (:signatures x) '([])))
        (is (= (:metadata x) {:private true}))) 

      ;; fn-private-no-args
      (let [x fn-private-no-args]
        (is (map? x))
        (is (= (-> x :location :start-row) 96))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 98))
        (is (= (-> x :location :end-col) 7))
        (is (= (-> x :public?) false))
        (is (= (-> x :private?) true))
        (is (= (:signatures x) '([])))
        (is (= (:metadata x) {:private true})))

      ;; fn-private-multi-arity
      ;;TODO: fix multi-artity parsing
      (let [x fn-private-multi-arity]
        (is (map? x))
        (is (= (-> x :location :start-row) 101))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 104))
        (is (= (-> x :location :end-col) 15))
        (is (= (-> x :public?) false))
        (is (= (-> x :private?) true))
        (is (= (:signatures x) '()))
        (is (= (:metadata x) {:private true})))

      ;; fn-private-inline
      (let [x fn-private-inline]
        (is (map? x))
        (is (= (-> x :location :start-row) 108))
        (is (= (-> x :location :start-col) 3))
        (is (= (-> x :location :end-row) 113))
        (is (= (-> x :location :end-col) 35))
        (is (= (-> x :public?) false))
        (is (= (-> x :private?) true))
        (is (= (:signatures x) '([x y z])))
        (is (= (-> x :metadata :added) "ADDED::0.9"))
        (is (= (-> x :metadata :author) "AUTHOR::fn-private-inline"))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-private-inline"))) 

      ;; fn-private-clj-only
      (let [x fn-private-clj-only]
        (is (map? x))
        (is (= (-> x :location :start-row) 117))
        (is (= (-> x :location :start-col) 9))
        (is (= (-> x :location :end-row) 122))
        (is (= (-> x :location :end-col) 19))
        (is (= (-> x :public?) false))
        (is (= (-> x :private?) true))
        (is (= (:signatures x) '([a b])))
        (is (= (-> x :metadata :added) "ADDED::0.10"))
        (is (= (-> x :metadata :author) "AUTHOR::fn-private-clj-only"))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-private-clj-only"))) 

      ;; fn-private-cljs-only
      (let [x fn-private-cljs-only]
        (is (map? x))
        (is (= (-> x :location :start-row) 126))
        (is (= (-> x :location :start-col) 8))
        (is (= (-> x :location :end-row) 131))
        (is (= (-> x :location :end-col) 18))
        (is (= (-> x :public?) false))
        (is (= (-> x :private?) true))
        (is (= (:signatures x) '([a b])))
        (is (= (-> x :metadata :added) "ADDED::0.11"))
        (is (= (-> x :metadata :author) "AUTHOR::fn-private-cljs-only"))
        (is (= (-> x :metadata :doc) "DOCSTRING::fn-private-cljs-only"))) 

      ;; var-public-meta-full
      (let [x var-public-meta-full]
        (is (map? x))
        (is (= (-> x :location :start-row) 135))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 139))
        (is (= (-> x :location :end-col) 5))
        (is (= (-> x :metadata :added) "ADDED::0.12"))
        (is (= (-> x :metadata :author) "AUTHOR::var-public-meta-full"))
        (is (= (-> x :metadata :doc) "DOCSTRING::var-public-meta-full"))) 

      ;; var-public-meta-docstring
      (let [x var-public-meta-docstring]
        (is (map? x))
        (is (= (-> x :location :start-row) 141))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row)  143))
        (is (= (-> x :location :end-col) 5))
        (is (= (-> x :metadata :doc) "DOCSTRING::var-public-meta-docstring"))) 

      ;; var-public-meta-none
      (let [x var-public-meta-none]
        (is (map? x))
        (is (= (-> x :location :start-row) 145))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row)  145))
        (is (= (-> x :location :end-col) 29))
        (is (nil? (:metadata x)))) 

      ;; var-private-meta-full
      (let [x var-private-meta-full]
        (is (map? x))
        (is (= (-> x :location :start-row) 147))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row) 152))
        (is (= (-> x :location :end-col) 5))
        (is (= (-> x :metadata :added) "ADDED::0.13"))
        (is (= (-> x :metadata :author) "AUTHOR::var-private-meta-full"))
        (is (= (-> x :metadata :doc) "DOCSTRING::var-private-meta-full"))) 

      ;; var-private-meta-docstring
      (let [x var-private-meta-docstring]
        (is (map? x))
        (is (= (-> x :location :start-row) 155))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row)  158))
        (is (= (-> x :location :end-col) 5))
        (is (= (-> x :metadata :doc) "DOCSTRING::var-private-meta-docstring"))) 

      ;; var-private-meta-none
      (let [x var-private-meta-none]
        (is (map? x))
        (is (= (-> x :location :start-row) 161))
        (is (= (-> x :location :start-col) 1))
        (is (= (-> x :location :end-row)  161))
        (is (= (-> x :location :end-col) 40))
        (is (= (:metadata x) {:private true}))) 

)))
