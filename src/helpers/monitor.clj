(ns helpers.monitor
    (:require [helpers.defs :refer [commands is-run? mls-sequence-time]]))


(defn all-commnds-part-sequence? [cmds seq-time]
    (every? #(<= (- (:time (second %)) (:time (first %))) seq-time) (partition 2 1 cmds)))

(defn- have-more-elements-added? [cmds-now cmds-old]
    (not (= (count cmds-now) (count cmds-old))))

(defn count-commands-next-sequence [index cmds-list seq-time]
    (if (or (>= (inc index) (count cmds-list)) (> (- (:time (nth cmds-list (inc index))) (:time (nth cmds-list index))) seq-time))
        (inc index)
        (recur (inc index) cmds-list seq-time)))

(defn wait-end-sequence [cmds-old-list seq-time]
    (let [cmds-now-list @commands]
        (when (and is-run? (have-more-elements-added? cmds-now-list cmds-old-list) (all-commnds-part-sequence? cmds-now-list seq-time))
            (Thread/sleep seq-time)
            (recur cmds-now-list seq-time))))

(defn count-keys-pressed-same-time [count key-focus keys-list time]
    (if (or (empty? keys-list) (> (- (:time (first keys-list )) (:time key-focus)) time))
        count
        (recur (inc count) key-focus (drop 1 keys-list) time)))

(defn monitor-commands []
    (while (or @is-run? (not (empty? @commands)))
        (do
            (wait-end-sequence [] mls-sequence-time)
            (when (not (empty? @commands))
                (let [cmds-list @commands
                      count-cmds-sequence (count-commands-next-sequence 0 cmds-list mls-sequence-time)
                      cmds-sequence (take count-cmds-sequence cmds-list)]
                    (reset! commands (drop count-cmds-sequence @commands)))))))