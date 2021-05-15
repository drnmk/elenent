(ns elenent.system
  (:require [com.stuartsierra.component :as component]
            [elenent.db :as db]
            [elenent.server :as server]))

(defrecord Status []
  component/Lifecycle
  (start [this]
    (assoc this :running? true))
  (stop [this]
    (assoc this :running? false)))

(defn make-system
  [configs]
  (component/system-map
   :status (->Status)
   :db (db/map->Db (:db configs))
   :server (component/using 
            (server/map->Server (:server configs))
            [:db])))
