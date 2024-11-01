(ns ft-ality.main
  (:gen-class)
  (:require 
    [helpers.file :refer [check-path]]
    [helpers.key :refer [create-frame]]
    [helpers.validator :refer [is-file-grammar-valid?]]))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (if (= 1 (count args)) 
    (when (and (not (nil? (check-path (last args)))) (is-file-grammar-valid? (last args)))
      (create-frame))
    (print "quantiidade de parametros errada")))
