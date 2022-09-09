(ns docist.util)

(defmacro try*
  [& body]
  `(try [~@body nil]
        (catch Throwable t#
          [nil {:err/message (.getMessage t#)
                :err/cause (.getCause t#)}])))
