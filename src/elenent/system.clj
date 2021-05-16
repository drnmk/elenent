(ns elenent.system
  (:require [com.stuartsierra.component :as component]
            [elenent.db :as eldb]
            [elenent.server :as elserver]))

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
   :db (eldb/map->Db (:db configs))
   :server (component/using 
            (elserver/map->Server (:server configs))
            [:db])))
