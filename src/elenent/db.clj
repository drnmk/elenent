(ns elenent.db
  (:require
   [com.stuartsierra.component :refer [Lifecycle]]
   [crux.api :refer [entity db submit-tx start-node]]))

(defn make-counter [num]
  {:crux.db/id :counter
   :num num})

(defn get-counter [conn]
  (entity
   (db conn)
   :counter))

(defn init-counter [conn]
  (submit-tx
   conn
   [[:crux.tx/put
     (make-counter 0)]]))

(defn update-counter [conn f]
  (let [bal (:num (get-counter conn))]
    (submit-tx
     conn
     [[:crux.tx/put
       (make-counter (f bal))]])))

(defrecord Db [options conn]
  Lifecycle
  (start [this]
    (let [conn (start-node {})]
      (init-counter conn)
      (assoc this :conn conn)))
  (stop [this]
    (when conn (.close conn))
    (assoc this :conn nil)))
