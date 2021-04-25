(ns elenent.model.contract
  (:require
   [clojure.spec.alpha :as s]
   [java-time :as t]
   [elenent.model.crux :refer :all]
   [elenent.model.breed :refer :all]
   [elenent.model.underlying :refer :all]))

(s/def :contract/name string?)
(s/def :contract/underlying underlyings)
(s/def :contract/notional int?) 
(s/def :contract/mature-on (partial instance? java.time.LocalDate))
(s/def :contract/breed breeds)
