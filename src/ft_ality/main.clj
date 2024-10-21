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
    (when (not (nil? (check-path (last args))))
      (create-frame)
      (is-file-grammar-valid? (last args)))
    (print "quantiidade de parametros errada")))
