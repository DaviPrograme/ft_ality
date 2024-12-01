(ns helpers.file
    (:require [clojure.java.io :as io]
              [helpers.aux :refer [error-msg]]))

(defn path-exists? [path]
  (.exists (io/file path)))

(defn is-file? [path]
  (let [file (io/file path)]
    (.isFile file)))

(defn check-path [path]
  (if (path-exists? path) 
    (if (is-file? path)
      true
      (error-msg "It is not a file."))
    (error-msg "File not found.")))

(defn get-content-file [path]
  (slurp path))

