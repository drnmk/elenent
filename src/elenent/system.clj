(ns elenent.system
  (:require [com.stuartsierra.component
             :refer [Lifecycle system-map using]]
            [elenent.data :as data]
            [elenent.server :as server]))

(defrecord Status []
  Lifecycle
  (start [this]
    (assoc this :running? true))
  (stop [this]
    (assoc this :running? false)))

(defn make-system
  [configs]
  (system-map
   :status (->Status)
   :db (data/map->Db (:db configs))
   :server (using 
            (server/map->Server (:server configs))
            [:db])))
