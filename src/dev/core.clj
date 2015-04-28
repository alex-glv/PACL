(ns dev.core
  (:require [instaparse.core :as insta]))

(def grammar (atom nil))
(def ignore (atom nil))
(defn get-contents [res-file]
  (slurp (clojure.java.io/file  (clojure.java.io/resource res-file))))


