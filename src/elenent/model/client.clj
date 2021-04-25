(ns elenent.model.client
  (:require
   [clojure.spec.alpha :as s]
   [elenent.model.crux :refer :all]
   [elenent.model.currency :refer :all]))

(s/def :client/legal-name string?)
(s/def :client/short-name string?)
(s/def :client/email string?)
(s/def :client/address string?)
(s/def :client/phone string?)
(s/def :client/base-currency currencies)
(s/def :client/functional-currency currencies)
(s/def :client/reporting-currency currencies)
(s/def :client/description string?)

(s/def :client/entity
  (s/keys :req [:crux.db/id
                :client/legal-name
                :client/short-name
                :client/email
                :client/address
                :client/phone
                :client/base-currency
                :client/functional-currency
                :client/reporting-currency
                :client/description]))
