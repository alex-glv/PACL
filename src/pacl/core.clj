(ns pacl.core
  (:require [pacl.reader :as rdr]
            [clojure.java.io :as io]
            [clojure.walk]
            [clojure.zip]
            ))

(defprotocol ZipperAst
  (init [this])
  (add-child [this tree child]))

(deftype AstTree [things]
  ZipperAst
  (init [this]
    (cond
      (vector? things) (clojure.zip/seq-zip things)
      (seq? things) (clojure.zip/vector-zip things)))

  (add-child [this tree child]
    (clojure.zip/append-child tree child)))


(defmulti process-ast-element
  (fn [el]
    (if (coll? el)
      (first el)
      )))

(defn process-tree-element
  ([tree form depth]
   (let [processed (process-ast-element form)
         inner-tree (if (and (coll? form) (not (empty? form)))
                      (walk-tree tree  (first form) #'process-tree-element (inc depth))
                      tree)]
     (if (not (nil? processed))
 ;;; todo: replace conj with higher-level tree manipulation functions
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

(defmethod process-ast-element :class_declaration_statement [el] {:class_name  (second (nth el 2))})
(defmethod process-ast-element :class_statement [el]
  (let [type (first (rest el))]
    (case type
      :method_modifiers
      (let [name (nth el 4)]
        {:class_member_name name})
      nil))
  )
(defmethod process-ast-element :extends_from [el] {:extends_form (last  (last  (last  (rest (nth el 2)))))})
(defmethod process-ast-element :member_modifier [el]  {:scope (first el) :type (first (second el))})
(defmethod process-ast-element :property [el] {:var_name (last (second el))})
(defmethod process-ast-element :default [el] nil)

