(ns helpers.monitor
    (:require [helpers.defs :refer [commands is-run? mls-sequence-time key-waiting-time]]))


(defn all-commnds-part-sequence? [cmds seq-time]
    (every? #(<= (Math/abs (- (:time (second %)) (:time (first %)))) seq-time) (partition 2 1 cmds)))

(defn- have-more-elements-added? [cmds-now cmds-old]
    (not (= (count cmds-now) (count cmds-old))))

(defn count-commands-next-sequence [index cmds-list seq-time]
    (if (or (>= (inc index) (count cmds-list)) (> (Math/abs (- (:time (nth cmds-list (inc index))) (:time (nth cmds-list index)))) seq-time))
        (inc index)
        (recur (inc index) cmds-list seq-time)))


(defn wait-end-sequence [seq-time]
    (let [cmds-now-list @commands
          now-time-millis (System/currentTimeMillis)
          last-element-part? (<= (Math/abs (- now-time-millis (get (last cmds-now-list) :time 0))) (* 4 seq-time))
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

(defn monitor-commands []
    (while (or @is-run? (not (empty? @commands)))
        (do
            (let [cmds-list (sort-by :time (wait-end-sequence mls-sequence-time))]
                (when (not (empty? cmds-list))
                    (let [count-cmds-sequence (count-commands-next-sequence 0 cmds-list mls-sequence-time)
                          cmds-sequence (take count-cmds-sequence cmds-list)
                          handle-cmds-sequence (handle-commands [] cmds-list key-waiting-time)]
                        ;; (println "ORIGINAL: " cmds-list )
                        ;; (println "TAKE: " cmds-sequence )
                        ;; (println "HANDLE: " handle-cmds-sequence "\n\n")
                        (reset! commands (drop count-cmds-sequence @commands))))))))