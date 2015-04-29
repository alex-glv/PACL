(ns dev.core
  (:require [instaparse.core :as insta]))

(def grammar (atom nil))
(def ignore (atom nil))
(defn get-contents [res-file]
  (slurp (clojure.java.io/file  (clojure.java.io/resource res-file))))


(defn parse-f [file-name]
  (reset! ignore (insta/parser (get-contents "whitespace.bnf")))
  (reset! grammar (insta/parser (get-contents "phpgrammar.bnf") :auto-whitespace @ignore))
  (grammar (get-contents file-name)))
