(ns helpers.aux
    (:require 
        [clojure.string :as str]))


(def special-keys #{"UP", "DOWN", "RIGHT", "LEFT", "SPACE"})

(defn remove-empty-lines [text]
    (->> (str/split-lines text) (map str/trim) (remove empty?)))

(defn normalize-spaces [s]
  (-> s (str/trim) (str/replace #"\s+\+" "+") (str/replace #"\+\s+" "+") (str/replace #"\s+," ",") (str/replace #",\s+" ",") (str/replace #"\s+" " ")))

(defn get-part-list [lines, index]
    (doall (map (fn [line] (normalize-spaces (str/upper-case (nth (str/split line #"-") index "")))) lines)))

(defn get-sections [content]
    (str/split content #"_+"))

