(ns elenent.db
  (:require
   [com.stuartsierra.component :as component]
   [crux.api :as c]))

(defrecord Db [options conn]
  component/Lifecycle
  (start [this]
    (let [conn (c/start-node {})]
      (assoc this :conn conn)))
  (stop [this]
    (when conn (.close conn))
    (assoc this :conn nil)))
