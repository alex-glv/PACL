(ns pacl.core
  (:require [pacl.reader :as rdr]
            [clojure.java.io :as io]
            [pacl.protocols :as p]
            [clojure.walk :as walk])
  (:import [java.io StringReader]
           [java_cup.runtime Scanner Symbol lr_parser]
           [org.eclipse.php.internal.core.ast.nodes AST]
           [org.eclipse.php.internal.core.ast.scanner.php55 PhpAstLexer PhpAstParser]
           [org.eclipse.php.internal.core PHPVersion]
           ))


(defn getParser [] 
  (let [version (PHPVersion/byAlias "php5.5")
        emptyReader (new java.io.StringReader "")
        strredr  (new java.io.StringReader "<?php echo 'Hello world!'; " )
        astObj (new AST emptyReader version false false)
        lexer (new PhpAstLexer strredr)]
    (doto lexer
      (.setAST astObj))
    (let [parser (new PhpAstParser (new PhpAstLexer emptyReader))]
      (.setAST parser astObj)
      (.setScanner parser lexer)
      parser)))
