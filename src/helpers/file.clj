(ns helpers.file
    (:require [clojure.java.io :as io]
              [helpers.aux :refer [error-msg]]))

(defn path-exists? [path]
  (.exists (io/file path)))

(defn is-file? [path]
  (let [file (io/file path)]
    (.isFile file)))

(defn has-permission? [path]
  (let [file (io/file path)]
    (.canRead file)))

(defn check-path [path]
  (cond
    (not (path-exists? path)) (error-msg "File not found.")
    (not (is-file? path)) (error-msg "It is not a file.")
    (not (has-permission? path)) (error-msg "Unable to open the file")
    :else true))

(defn get-content-file [path]
  (slurp path))

