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
  ([tree form depth]
   (let [processed (processtreepart form)
         inner-tree (if (and (coll? form) (not (empty? form)))
                      (walk-tree tree  (first form) #'tree-assembler (inc depth))
                      tree)]
     (if (not (nil? processed))
       (let [new-tree (conj tree {:element processed :depth depth})]
         new-tree)
       inner-tree)
     )))

(defn walk-tree
  ([top processor-fn]
   (walk-tree '() top processor-fn 0))
  ([tree top processor-fn depth]
   (let [new-tree (processor-fn tree top depth)]
     (if (and (coll? top) (not (empty? top)))
       (walk-tree new-tree (rest top) processor-fn depth)
       new-tree
       ))
   ))

(defn replace-last [coll item]
  (let [coll-last (last coll)]
    (conj (pop coll) [coll-last [item]])))

(defmethod processtreepart :class_declaration_statement [el] {:class_name  (second (nth el 2))})

(defmethod processtreepart :class_statement [el]
  (let [type (first (rest el))]
    (case type
      :method_modifiers
      (let [name (nth el 4)]
        {:class_member_name name})
      nil))
  )

(defmethod processtreepart :extends_from [el] {:extends_form (last  (last  (last  (rest (nth el 2)))))})
(defmethod processtreepart :member_modifier [el]  {:scope (first el) :type (first (second el))})
(defmethod processtreepart :property [el] {:var_name (last (second el))})
(defmethod processtreepart :default [el] nil)

