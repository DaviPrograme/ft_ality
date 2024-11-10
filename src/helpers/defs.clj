(ns helpers.defs)

(def special-keys #{"UP", "DOWN", "RIGHT", "LEFT", "SPACE"})

(def commands (atom []))

(def combos-tree (atom nil))

(def is-run? (atom true))

(def mls-sequence-time 400)

(def mls-tolerance-press-button-time 100)