(ns dev.core
  (:require [pacl.reader :as rdr]
            [clojure.java.io :as io]
            [pacl.protocols :as p]
            [clojure.walk :as walk] :reload-all))

(def fil-cl (rdr/get-ast (io/resource "class.php")))

(defmulti processtreepart
  (fn [el]
    (if (coll? el)
      (first el)
      el )
    ))

(defn walk-tree [tree]
  (trampoline processtreepart tree ))

(defmethod processtreepart :class_declaration_statement [el] (println "class declaration") #(processtreepart (first  (rest el))) )
(defmethod processtreepart :class_statement [el] (println "class statement") #(processtreepart (first  (rest el))))
(defmethod processtreepart :method_modifiers [el] (println "methodmodifiers") #(processtreepart (first  (rest el))))
(defmethod processtreepart :variable_modifiers [el] (println "variable modifiers") #(processtreepart (first (rest el))))
(defmethod processtreepart :default [el]
  
  (if (coll? el)
    #(processtreepart  (first (rest  el)))
    nil))

