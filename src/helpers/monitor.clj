(ns helpers.monitor
    (:require [helpers.defs.atoms :refer [commands combos-tree is-run?]]
              [helpers.defs.constants :refer [mls-sequence-time mls-tolerance-press-button-time]]))


(defn all-commnds-part-sequence? [cmds seq-time]
    (every? #(<= (Math/abs (- (:time (second %)) (:time (first %)))) seq-time) (partition 2 1 cmds)))

(defn- have-more-elements-added? [cmds-now cmds-old]
    (not (= (count cmds-now) (count cmds-old))))

(defn count-commands-next-sequence [len-seq cmds-list seq-time]
    (if (or (>= (inc len-seq) (count cmds-list)) (> (Math/abs (- (:time (nth cmds-list (inc len-seq))) (:time (nth cmds-list len-seq)))) seq-time))
        (inc len-seq)
        (recur (inc len-seq) cmds-list seq-time)))


(defn wait-end-sequence [seq-time]
    (let [cmds-now-list @commands
          now-time-millis (System/currentTimeMillis)
          last-element-part? (<= (Math/abs (- now-time-millis (get (last cmds-now-list) :time 0))) (* 2 seq-time))
          empty-list? (empty? cmds-now-list)]
        (if (not (and @is-run? (or (empty? cmds-now-list) last-element-part?)))
            cmds-now-list
            (do
                (Thread/sleep seq-time)
                (recur seq-time)))))

(defn count-keys-pressed-same-time [count key-focus keys-list time]
    (if (or (empty? keys-list) (> (Math/abs (- (:time (first keys-list )) (:time key-focus))) time))
        count
        (recur (inc count) key-focus (drop 1 keys-list) time)))

(defn- set-handle [set-value]
    (if (= (count set-value) 1) (first set-value) set-value))

(defn handle-commands [new-cmds-list old-cmds-list wait-time]
    (if (empty? old-cmds-list)
        new-cmds-list
        (let [next-cmd (first old-cmds-list)
              num-keys-pressed-same-time (count-keys-pressed-same-time 0 next-cmd (drop 1 old-cmds-list) wait-time)
              rest-cmds-list (drop (inc num-keys-pressed-same-time) old-cmds-list)
              next-command-handled 
                (if (zero? num-keys-pressed-same-time) 
                    (str (:key next-cmd)) 
                    (set-handle (set (map #(str (:key %)) (take (inc num-keys-pressed-same-time) old-cmds-list)))))]
            (recur (conj new-cmds-list next-command-handled) rest-cmds-list wait-time))))

(defn- remove-ending-str [string end-str]
    (subs string 0 (- (count string) (count end-str))))


(defn- translate-keys-to-strikes [keys-map keys-list]
     (map (fn [key]
         (if (set? key)
            (let [concat-str " + "]
            (remove-ending-str
                (reduce (fn [acc el]
                    (str acc (get keys-map el "") concat-str))
                    ""
                    key) concat-str))
           (get keys-map key "")))
       keys-list))

(defn is-special-combo? [tree strike-list]
    (let [strike-focus (first strike-list)
          rest-strike-list (drop 1 strike-list)
          branche (get @(get tree :branches) strike-focus)
          next-node (if (nil? branche) nil branche)]
        (if (or (empty? rest-strike-list) (nil? next-node))
            (if (nil? next-node)
                nil
                @(:special next-node))
            (recur next-node rest-strike-list))))

(defn- remove-read-commands [commands-atom count-sequence]
    (reset! commands-atom (drop count-sequence @commands-atom)))

(defn- print-strikes [keys-map cmds mls-seq-time msl-tolerance-time]
     (let [count-cmds-sequence (count-commands-next-sequence 0 cmds mls-seq-time)
           cmds-sequence (take count-cmds-sequence cmds)
           handle-cmds-sequence (handle-commands [] cmds msl-tolerance-time)
           is-special? (is-special-combo? @combos-tree handle-cmds-sequence)
           convert-to-strike (reduce #(str %1 (if (empty? %1) "" ", ") %2) "" (translate-keys-to-strikes keys-map handle-cmds-sequence))]
        (println "KEYS: " handle-cmds-sequence )
        (println "STRIKES: " convert-to-strike)
        (when (not (nil? is-special?))
            (println "SPECIAL: " is-special?))
        (print "\n\n")
        (remove-read-commands commands count-cmds-sequence )))

(defn monitor-commands [keys-map]
    (while (or @is-run? (not (empty? @commands)))
        (let [cmds-list (sort-by :time (wait-end-sequence mls-sequence-time))]
            (when (not (empty? cmds-list))
                (print-strikes keys-map cmds-list mls-sequence-time mls-tolerance-press-button-time)))))