(ns elenent.model.grouping
  (:require
   [clojure.spec.alpha :as s]))

(s/def :grouping/code string?)
(s/def :grouping/description string?)

(s/def :grouping/entity
  (s/keys :req [:crux.db/id
                :grouping/code
                :grouping/description]))
