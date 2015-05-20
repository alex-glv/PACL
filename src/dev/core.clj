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

(defn tree-assembler [element]
  (processtreepart element)
  (fn [] (walk-tree element tree-assembler)))


(defn walk-tree [tree processor-fn]
  (map (fn [element]
         (trampoline
          (fn [el]
            (when (coll? el)
              (processor-fn el))) element))
       tree))

(defmethod processtreepart :class_declaration_statement [el] (println "class declaration" el) )
(defmethod processtreepart :class_statement [el] (println "class statement" :statement))
(defmethod processtreepart :method_modifiers [el] (println "methodmodifiers") )
(defmethod processtreepart :property_list [el] (println "property_list" (second el)))
(defmethod processtreepart :variable_modifiers [el] (println "variable modifiers") )
(defmethod processtreepart :property [el] (println "property" el))
(defmethod processtreepart :default [el])

