(ns helpers.file
    (:require [clojure.java.io :as io]))

(defn path-exists? [path]
  (.exists (io/file path)))

(defn is-file? [path]
  (let [file (io/file path)]
    (.isFile file)))

(defn check-path [path]
  (if (path-exists? path) 
    (if (is-file? path)
      true
      (print "error: não é um arquivo"))
    (print "error: arquivo não encontrado")))

(defn get-content-file [path]
  (slurp path))

