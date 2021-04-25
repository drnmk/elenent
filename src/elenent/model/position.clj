(ns elenent.model.position
  (:require
   [clojure.spec.alpha :as s]
   [elenent.model.crux :refer :all]
   [elenent.model.stances :refer :all]))

(s/def :position/name string?)
(s/def :position/stance stances)
(s/def :position/quantity int?)

(s/def :position/entity
  (s/keys :req [:crux.db/id
                :position/name
                :position/stance
                :position/quantity]))
