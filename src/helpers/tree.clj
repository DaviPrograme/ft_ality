(ns helpers.tree 
    (:require 
        [helpers.aux :refer [get-sections remove-empty-lines get-part-list]]))


(defn map-commands [content]
    (let [keys (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 0)
          strokes (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 1)]
        (zipmap keys strokes)))
