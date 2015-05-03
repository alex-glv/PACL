(ns pacl.reader
  (:require [clojure.walk :as walk]
            [instaparse.core :as insta]
            [clojure.java.io :as io]))

(def ^:dynamic *debug* false)
(def ^:dynamic *debug-fn* 'println)

(defn get-contents
  ([res-file] (slurp res-file))
  ([res-file fun] (slurp (fun res-file))))

(defn get-files-by-mask [mask])
(defn get-ast [file])
(defn scan-dir [dir]
  (when *debug*
    (*debug-fn* "Scanning " dir))
  (file-seq (clojure.java.io/file dir)))

(def ignore
  (insta/parser (get-contents "whitespace.bnf" (comp io/file io/resource))))

(def grammar
  (insta/parser (get-contents "phpgrammar.bnf" (comp io/file io/resource)) :auto-whitespace ignore))

(defn parse-file [file-res]
  (when *debug*
    (*debug-fn* "Parsing " file-res))
  (grammar (get-contents file-res))
  )
