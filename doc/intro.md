# Introduction to pacl
## Protocols

(defprotocol CompletableEntity 
  (responds-to [])
  (will-respond-to [context])
  )

(defprotocol Context
  (wrapped-with [left])
  (in-file [file]))

class
context : ->(input) | ::(input)

interface
context : ->(input) | ::(input)

function
context : (input)

variable
context : (inpunt)


reader/processor
cache
  -> cache storage
cache-store (obj)
cache-retrieve (--)
cache-rebuild

runner
  -> filewatch
  -> build cache

