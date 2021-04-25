(ns elenent.comps.contract.mock
  
  (:require
   [java-time :as t]
   [clj-uuid :as u]))

(def mock-futures
  [{:crux.db/id 13245678
    :contract/name "RGDS1FDS"
    :contract/breed :future
    :contract/counterparty :BOA
    :contract/underlying :TU
    :contract/notional 200000
    :contract/mature-on (t/local-date 2021 6 30)}
   {:crux.db/id 13245679
    :contract/name "RBCTY1FDA"
    :contract/breed :future
    :contract/counterparty :GS
    :contract/underlying :TY
    :contract/notional 100000
    :contract/mature-on (t/local-date 2021 3 31)}
   {:crux.db/id 13245680
    :contract/name "RBCTY1FDJ"
    :contract/breed :future
    :contract/counterparty :WLF
    :contract/underlying :TY
    :contract/notional 100000
    :contract/mature-on (t/local-date 2021 3 31)}])

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
