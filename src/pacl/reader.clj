(ns pacl.reader
  (:require [clojure.walk :as walk]
            [clojure.java.io :as io])
  (:import [org.eclipse.php.internal.core.compiler.ast.parser]))

(def ^:dynamic *debug* false)
(def ^:dynamic *debug-fn* 'println)

(defn get-contents
  ;; replace slurp with buffered reader
  ([res-file] (slurp res-file))
  ([res-file fun] (slurp (fun res-file))))

(defn get-files-by-mask [mask])



(defn scan-dir [dir]
  (when *debug*
    (*debug-fn* "Scanning " dir))
  (file-seq (clojure.java.io/file dir)))

(def context-map
  [{:prefix "::" :recipient :static-method}
   {:prefix "->" :recipient :method}
   {:prefix "$" :recipient :all}])
