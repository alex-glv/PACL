(ns pacl.core
  (:require [pacl.reader :as rdr]
            [clojure.java.io :as io]
            [pacl.protocols :as p]
            [clojure.walk :as walk] :reload-all))

(declare walk-tree replace-last)

(defmulti processtreepart
  (fn [el]
    (if (coll? el)
      (first el)
      )))


(defn tree-assembler
  ([ form]
   (let [processed (processtreepart form)
         children (if (coll? form)
                    (map (fn [el] (walk-tree el #'tree-assembler)) form)
                    nil)]
     (if (nil? processed)
       (if (empty? children)
         nil
         children))
       (if (empty? children)
         processed
         [processed [children]]))))


(defn walk-tree [top processor-fn]
  (let [children (map (fn [element]
                        (let [res (processor-fn element)]
                          res))
                      top)
        non-empty-ch (filter (complement nil?)  children)]

    non-empty-ch))

(defn replace-last [coll item]
  (let [coll-last (last coll)]
    (conj (pop coll) [coll-last [item]])))

(defmethod processtreepart :class_declaration_statement [el] :class_declaration_statement)
(defmethod processtreepart :class_statement [el]  :statement)
(defmethod processtreepart :method_modifiers [el]  :method_modifiers)
(defmethod processtreepart :property_list [el]  :property_list)
(defmethod processtreepart :variable_modifiers [el]  :variable_modifiers)
(defmethod processtreepart :property [el]  :property)
(defmethod processtreepart :default [el] nil)

