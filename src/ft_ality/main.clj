(ns ft-ality.main
  (:gen-class)
  (:require
    [helpers.aux :refer [keys-commands-map error-msg]]
    [helpers.file :refer [check-path get-content-file]]
    [helpers.window :refer [create-frame]]
    [helpers.tree :refer [build-tree]]
    [helpers.monitor :refer [monitor-commands]]
    [helpers.validator :refer [is-file-grammar-valid?]]))


(defn -main
  [& args]
  (if (= 1 (count args))
    (when (and (not (nil? (check-path (last args)))) (is-file-grammar-valid? (last args)))
        (let [content (get-content-file (last args))
              keys-map (keys-commands-map content)]
          (build-tree content)
          (create-frame keys-map)
          (monitor-commands keys-map)))
    (error-msg "Program expects to receive a valid grammar file.")))
