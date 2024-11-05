(ns helpers.tree 
    (:require 
        [helpers.aux :refer [get-sections remove-empty-lines get-part-list normalize-spaces]]
        [helpers.defs :refer [combos-tree]]
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
          strikes (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 1)]
        (zipmap keys strikes)))

(defn strikes-commands-map [content]
    (let [keys (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 0)
          strikes (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 1)]
        (zipmap strikes keys)))

(defn filter-empty-string [list]
    (filter seq list))

(defn treat-combined-strike [strike]
    (set (filter-empty-string  (str/split strike #"\+"))))

(defn build-combo-map [combo]
    (let [name-combo  (normalize-spaces (nth (get-part-list [combo] 0) 0))
          combo-stroke (doall (map #(if (str/includes? % "+") (treat-combined-strike %) %) (filter-empty-string (str/split (normalize-spaces(nth (get-part-list [combo] 1) 0)) #","))))]
        {:name name-combo :list combo-stroke}))

(defn add-node-tree [node combo-name strikes-list]
    (let [strike-now (first strikes-list)
          rest-strikes-list (rest strikes-list)
          new-node (create-node strike-now nil)]
        (when (not (contains? @(:branches node) strike-now ))
            (insert-branche-into-node node new-node))
        (when (empty? rest-strikes-list)
            (reset! (:special (get @(:branches node) strike-now)) combo-name))
        (when (not (empty? rest-strikes-list))
            (recur (get @(:branches node) strike-now) combo-name rest-strikes-list))))

(defn translate-strikes-to-keys [strikes-map strikes-list]
    (doall (map (fn [x] (if (set? x) (set (map #(get strikes-map %) x)) (get strikes-map x))) strikes-list)))

(defn build-tree [content]
    (let [combos-list (remove-empty-lines (nth (get-sections content) 1))
          combos-map (map build-combo-map combos-list)
          strikes-map (strikes-commands-map content)]
        (reset! combos-tree (create-node "root tree" "root tree"))
        (run! #(add-node-tree @combos-tree (:name %) (translate-strikes-to-keys strikes-map (:list %)) ) combos-map)))