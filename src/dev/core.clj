(ns dev.core
  (:require [pacl.reader :as rdr]
            [clojure.java.io :as io]
            [pacl.protocols :as p]
            [clojure.walk :as walk] :reload-all))
(defmulti ProcessTreePart
  (fn [el] (if (coll? el)
             (first el)
             nil)
    ))

(def fil-cl (rdr/get-ast (io/resource "class.php")))

(defmethod ProcessTreePart :class_declaration_statement [el] (println "from-class:") (prn el) el)
(defmethod ProcessTreePart :class_statement [el] (println "class-statements:") (prn el) el)
(defmethod ProcessTreePart :default [el] el)

(defn walk-tree [tree]
  (walk/prewalk #'ProcessTreePart tree)
  )

