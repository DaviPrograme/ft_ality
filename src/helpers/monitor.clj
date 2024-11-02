(ns helpers.monitor
    (:require [helpers.defs :refer [commands]]))


(defn all-commnds-part-sequence? [cmds seq-time]
    (every? #(<= (- (:time (second %)) (:time (first %))) seq-time) (partition 2 1 cmds)))

(defn- have-more-elements-added? [cmds-now cmds-old]
    (not (= (count cmds-now) (count cmds-old))))

(defn monitor-sequence [cmds-old-list seq-time]
    (let [cmds-now-list @commands]
        (when (and (have-more-elements-added? cmds-now-list cmds-old-list) (all-commnds-part-sequence? cmds-now-list seq-time))
            (Thread/sleep seq-time)
            (recur cmds-now-list seq-time))))