(ns elenent.db
  (:require
   [com.stuartsierra.component :as component]
   [crux.api :as crux]))

(defn make-counter [num]
  {:crux.db/id :counter
   :num num})

(defn get-counter [conn]
  (crux/entity
   (crux/db conn)
   :counter))

(defn init-counter [conn]
  (crux/submit-tx
   conn
   [[:crux.tx/put
     (make-counter 0)]]))

(defn update-counter [conn f]
  (let [bal (:num (get-counter conn))]
    (crux/submit-tx
     conn
     [[:crux.tx/put
       (make-counter (f bal))]])))

(defrecord Db [options conn]
  component/Lifecycle
  (start [this]
    (let [conn (crux/start-node {})]
      (init-counter conn)
      (assoc this :conn conn)))
  (stop [this]
    (when conn (.close conn))
    (assoc this :conn nil)))
