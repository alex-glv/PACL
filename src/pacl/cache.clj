(ns pacl.cache)

(defprotocol CacheEngine
  (store [obj])
  (retrieve [obj])
  (truncate []))
