(ns helpers.tree 
    (:require 
        [helpers.aux :refer [get-sections remove-empty-lines get-part-list]]))


(defn create-node [key name]
    {
        :key key
        :special (atom name)
        :branches (atom {})
    })

(defn insert-branche-into-node [node-base node-branch]
    (swap! (:branches node-base) assoc (:key node-branch) node-branch))

(defn map-commands [content]
    (let [keys (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 0)
          strokes (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 1)]
        (zipmap keys strokes)))
