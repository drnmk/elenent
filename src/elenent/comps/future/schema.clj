(ns elenent.comps.future.schema
  (:require
   [clojure.spec.alpha :as s]
   [java-time :as t]))

(s/def :crux.db/id uuid?)
(s/def :future/underlying #{:bond :emini})
(s/def :future/cusip string?) ;; e.g. FVU1
(s/def :future/mulitplier int?) ;; e.g. 100000
(s/def :future/mature-on
  (partial instance? java.time.LocalDate ))
;; e.g. 
;; (s/valid? :future/mature-on (t/local-date 2015 3 4))

(s/def :future/entity
  (s/keys :req [:crux.db/id
                :future/underlying 
                :future/cusip
                :future/multiplier
                :future/mature-on]))
