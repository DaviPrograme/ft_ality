(ns helpers.tree 
    (:require 
        [helpers.aux :refer [get-sections remove-empty-lines get-part-list normalize-spaces]]
        [clojure.string :as str]))


(defn create-node [key name]
    {
        :key key
        :special (atom name)
        :branches (atom {})
    })

(defn insert-branche-into-node [node-base node-branch]
    (swap! (:branches node-base) assoc (:key node-branch) node-branch))

(defn keys-commands-map [content]
    (let [keys (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 0)
          strokes (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 1)]
        (zipmap keys strokes)))

(defn strokes-commands-map [content]
    (let [keys (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 0)
          strokes (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 1)]
        (zipmap strokes keys)))

(defn- filter-empty-string [list]
    (filter seq list))

(defn- treat-combined-strike [strike]
    (set (filter-empty-string  (str/split strike #"+"))))

(defn build-combo-map [combo]
    (let [name-combo  (normalize-spaces (get-part-list combo 0))
          combo-stroke  (map #(if (str/includes? % "+") (treat-combined-strike %) %) (filter-empty-string (str/split (normalize-spaces (get-part-list combo 1)) #",")))]
          combo-stroke))

(defn build-tree [content]
    (let [combos-list (remove-empty-lines (nth (get-sections content) 1))
          strokes-map (strokes-commands-map content)]
        (println (nth combos-list 0))))