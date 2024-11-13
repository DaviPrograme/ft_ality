(ns helpers.validator
    (:require
        [helpers.defs.constants :refer [special-keys]]
        [helpers.file :refer [get-content-file]]
        [helpers.aux :refer [
                remove-empty-lines
                get-part-list
                get-sections
                error-msg
            ]
        ]
        [clojure.string :as str]
        [clojure.set :as set]))


(defn lines-with-other-characters [lines]
    (filter  #(re-find #"[^_]" %) lines))

(defn is-section-separator-valid? [content, print-error?]
    (let [  lines (str/split-lines content)
            lines-with-separator (filter #(re-find #"_+" %) lines)
            lines-with-others-chars (lines-with-other-characters lines-with-separator)]
                (if (and (= (count lines-with-separator) 1) (= (count lines-with-others-chars) 0) ) 
                    true
                    (do (when print-error?
                            (if (= (count lines-with-separator) (count lines-with-others-chars ))
                                (error-msg "The section separator line was not found.")
                                (error-msg "The underscore should only be used in the section separator line.")))
                        false))))

(defn- empty-command-section-error? [section, section-name, print-error?]
    (if (empty? section)
        (do (when print-error?
                (error-msg (str "The " section-name " section was not found")))
            false)
        true))

(defn- command-section-no-content-error? [lines, print-error?]
    (if (zero? (count lines))
                (do (when print-error?
                        (error-msg "No content was found in the basic commands section."))
                    false)
                true))

(defn single-dash? [text]
    (= 1 (count (filter #(= \- %) text))))

(defn all-lines-have-just-one-dash? [lines, print-error?]
    (if (some #(= % false) (map single-dash? lines))
        (do (when print-error?
                (error-msg "All lines with content should have only one dash."))
            false)
        true))

(defn is-char? [text]
    (and (= 1 (count text)) (Character/isLetter (first text))))

(defn is-special-key? [text]
    (contains? special-keys text))

(defn keys-recognized? [keys, print-error?]
    (if (zero? (count (filter #(not (or (is-char? %) (is-special-key? %))) keys)))
        true
        (do (when print-error?
                (error-msg "The only valid keys are letters and those representing 'Space', 'Up', 'Down', 'Right,' and 'Left'."))
            false)))

(defn repeated-keys? [keys, print-error?]
    (let [set-keys (set keys)]
        (if (= (count keys) (count set-keys))
            true
            (do (when print-error?
                (error-msg "A key can only represent a single strike."))
            false))))


(defn repeated-strikes? [strikes, print-error?]
    (let [set-strikes (set strikes)]
        (if (= (count strikes) (count set-strikes))
            true
            (do (when print-error?
                (error-msg "A strike can only be executed by a single key."))
            false))))

(defn is-section-commands-valid? [content, print-error?]
    (let [section-commands (nth (get-sections content) 0)
         lines-with-content-list (remove-empty-lines section-commands)
         keys-list (get-part-list lines-with-content-list 0)
         strikes-list (get-part-list lines-with-content-list 1)]
         (and
            (empty-command-section-error? section-commands, "basic commands" ,print-error?)
            (command-section-no-content-error? lines-with-content-list print-error?)
            (all-lines-have-just-one-dash? lines-with-content-list print-error?)
            (keys-recognized? keys-list print-error?)
            (repeated-keys? keys-list print-error?)
            (repeated-strikes? strikes-list print-error?))))


(defn all-combo-names-have-content? [lines, print-error?]
    (if (some #(= % true) (map #(empty? %) lines))
        (do (when print-error?
                (error-msg "All combos need to have a name."))
            false)
        true))

(defn combos-to-strikes [combos]
    (set (apply concat (map #(str/split % #"[,+]") combos))))

(defn strikes-not-found-error [registered-strikes, strikes, print-error?]
    (let [intersec (set/intersection strikes registered-strikes)
         diff (set/difference strikes intersec)]
         (if (zero? (count diff))
            true
            (do (when print-error?
                    (error-msg (str "The following strikes have not been registered: " diff)))
                false))))

(defn is-section-combos-valid? [content, print-error?]
    (let [registered-strikes (set (get-part-list (remove-empty-lines (nth (get-sections content) 0 "")) 1))
         section-combos (nth (get-sections content) 1 "")
         lines-with-content-list (remove-empty-lines section-combos)
         combos-name (get-part-list lines-with-content-list 0)
         combos (get-part-list lines-with-content-list 1)
         strikes (combos-to-strikes combos)]
         (and
            (empty-command-section-error? section-combos, "combo" ,print-error?)
            (command-section-no-content-error? lines-with-content-list print-error?)
            (all-lines-have-just-one-dash? lines-with-content-list print-error?)
            (all-combo-names-have-content? combos-name print-error?)
            (strikes-not-found-error registered-strikes, strikes, print-error?))))

(defn is-file-grammar-valid? [path]
    (let [content (get-content-file path)]
        (and (is-section-separator-valid? content, true) (is-section-commands-valid? content, true) (is-section-combos-valid? content, true))))