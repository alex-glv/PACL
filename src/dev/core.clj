(ns dev.core
  (:require [pacl.reader :as rdr]
            [clojure.java.io :as io]
            [pacl.protocols :as p]
            [clojure.walk :as walk] :reload-all))

(declare walk-tree)

(defmulti processtreepart
  (fn [el]
    (if (coll? el)
      (first el))))


(defn tree-assembler [current-tree element]
  (let [processed (processtreepart element)
        tree (if (nil? processed) current-tree (conj current-tree processed))]
    (fn [] (walk-tree element tree tree-assembler))
    ))

(defn walk-tree
  ([top processor-fn]
   (walk-tree top [] processor-fn))
  ([top tree processor-fn]
   (let [ only-colls (filter coll? top)  ]
     (if (empty? only-colls)
       tree
       (map (fn [element]
              (trampoline (fn [el] (processor-fn tree el)) element))
            only-colls)))))

(defmethod processtreepart :class_declaration_statement [el]  (nth el 2))
(defmethod processtreepart :class_statement [el]  :statement)
(defmethod processtreepart :method_modifiers [el]  :method_modifiers)
(defmethod processtreepart :property_list [el]  :property_list)
(defmethod processtreepart :variable_modifiers [el]  :variable_modifiers)
(defmethod processtreepart :property [el]  :property)
(defmethod processtreepart :default [el])

