(ns pacl.protocols)

(defprotocol PhpCompleatableEntity
  (will-respond-to [this context]))

(deftype PhpClassMethod [visibility name arguments]
  PhpCompleatableEntity
  (will-respond-to [this context])
  )

(deftype PhpClassProperty [visibility name defvalue]
  PhpCompleatableEntity
  (will-respond-to [this context]))
