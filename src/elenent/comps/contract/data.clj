(ns elenent.comps.contract.data
  (:require
   [java-time :as t]
   [clj-uuid :as u]
   [crux.api :as c]
   [clojure.spec.alpha :as s]
   [elenent.model.contract :refer :all]
   [elenent.model.underlying :refer :all]
   [elenent.database.control :refer [cn]]))

(defn write-future-!
  [underlying cusip multiplier mature-on]
  (c/submit-tx
   cn
   [[:crux.tx/put
     (s/conform :future/entity
      {:crux.db/id (u/v1)
       :future/underlying underlying
       :future/cusip cusip
       :future/multiplier multiplier
       :future/mature-on mature-on})]]))

(defn read-futures []
  (c/q
   (c/db cn)
   '{:find [id underlying cusip multiplier mature-on]
     :where [[e :crux.db/id id]
             [e :future/underlying underlying]
             [e :future/cusip cusip]
             [e :future/multiplier multiplier]
             [e :future/mature-on mature-on]]}))

;; e.g.
;; (write-future :us-treasury-bond "BBB" 100123 (t/local-date 2011 2 3))

(def mock-swaps-interest-rate
  {:crux.db/id 11130002
    :contract/name "RGDS1FDA"
    :contract/breed :swap
    :contract/underlying :TU
    :contract/notional 200000
    :contract/mature-on (t/local-date 2021 6 30)
    :contract/receive-leg {:counterparty :BOA
                           :rate-type :libor-3m
                           :interest-rate 3.1
                           :currency :usd}
    :contract/pay-leg {:counterparty :client
                       :interest-rate :fixed
                       :currency :usd}})

(c/submit-tx
   cn
   [[:crux.tx/put
     {:crux.db/id 11130002
      :contract/name "RGDS1FDA"
      :contract/breed :swap
      :contract/underlying :TU
      :contract/notional 200000
      :contract/mature-on (t/local-date 2021 6 30)
      :contract/receive-leg {:counterparty :BOA
                             :rate-type :libor-3m
                             :interest-rate 3.1
                             :currency :usd}
      :contract/pay-leg {:counterparty :client
                         :interest-rate :fixed
                         :currency :usd}}]])

(c/q
 (c/db cn)
 '{:find [c1 b u]
   :where [[c1 :contract/breed b]
           [c1 :contract/underlying u]]})
