(ns helpers.aux
    (:require 
        [clojure.string :as str]
        [helpers.defs :refer [red-color white-color]]))


(defn remove-empty-lines [text]
    (->> (str/split-lines text) (map str/trim) (remove empty?)))

(defn normalize-spaces [s]
  (-> s (str/trim) (str/replace #"\s+\+" "+") (str/replace #"\+\s+" "+") (str/replace #"\s+," ",") (str/replace #",\s+" ",") (str/replace #"\s+" " ")))

(defn get-part-list [lines, index]
    (doall (map (fn [line] (normalize-spaces (str/upper-case (nth (str/split line #"-") index "")))) lines)))

(defn get-sections [content]
    (str/split content #"_+"))

(defn keys-commands-map [content]
    (let [keys (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 0)
          strikes (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 1)]
        (zipmap keys strikes)))

(defn strikes-commands-map [content]
    (let [keys (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 0)
          strikes (get-part-list (remove-empty-lines (nth (get-sections content) 0)) 1)]
        (zipmap strikes keys)))

(defn is-recognized-key? [key keys-map]
    (not (nil? (get keys-map key))))

(defn error-msg [msg]
    (println (str red-color "ERROR: " white-color msg)))
