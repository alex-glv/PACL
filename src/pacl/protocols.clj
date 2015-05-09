(ns pacl.protocols)

(defprotocol PhpCompleatableEntity
  (will-respond-to [context]))

(deftype PhpClass [file class methods properties]
  PhpCompleatableEntity
  (will-respond-to [_]))

(deftype PhpClassMethod [visibility name arguments]
  PhpCompleatableEntity
  (will-respond-to [_]))

(deftype PhpClassProperty [visibility name defvalue]
  PhpCompleatableEntity
  (will-respond-to [_]))
