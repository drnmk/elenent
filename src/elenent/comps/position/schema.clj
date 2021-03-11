(ns elenent.position.schema
  (:require
   [clojure.spec.alpha :as s]
   [java-time :as t]))

(s/def :position/entity
  (s/keys :req [:crux.db/id
                :position/cusip
                :position/open-on
                :position/close-on
                :position/groupings]))
