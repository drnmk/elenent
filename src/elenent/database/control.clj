(ns elenent.database.control
  (:require
   [crux.api :as c]
   [elenent.database.tool :refer :all]))

(def cn (c/start-node {}))
