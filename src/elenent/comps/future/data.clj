(ns elenent.comps.future.data
  (:require
   [java-time :as t]
   [clj-uuid :as u]
   [crux.api :as c]
   [clojure.spec.alpha :as s]
   [elenent.database.control :as database]
   [elenent.comps.future.schema :refer :all]))

(defn write-future
  [underlying cusip multiplier mature-on]
  (c/submit-tx
   database/cn
   [[:crux.tx/put
     (s/conform :future/entity
      {:crux.db/id (u/v1)
       :future/underlying underlying
       :future/cusip cusip
       :future/multiplier multiplier
       :future/mature-on mature-on})]]))

(defn read-futures []
  (c/q
   (c/db database/cn)
   '{:find [id underlying cusip multiplier mature-on]
     :where [[e :crux.db/id id]
             [e :future/underlying underlying]
             [e :future/cusip cusip]
             [e :future/multiplier multiplier]
             [e :future/mature-on mature-on]]}))

;; e.g.
(write-future :bond "BBB" 100123 (t/local-date 2011 2 3))
