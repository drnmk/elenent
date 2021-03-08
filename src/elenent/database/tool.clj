(ns elenent.database.tool
  (:require
   [java-time :as jtm]
   [clj-uuid :as uid]))

(defn str->uuid [s]
  (java.util.UUID/fromString s))
