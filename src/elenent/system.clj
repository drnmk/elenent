(ns elenent.system
  (:require [com.stuartsierra.component
             :refer [Lifecycle system-map using]]
            [elenent.db :as eldb]
            [elenent.server :as elserver]))

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
   :db (eldb/map->Db (:db configs))
   :server (using 
            (elserver/map->Server (:server configs))
            [:db])))
