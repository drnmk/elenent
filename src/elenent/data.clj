(ns elenent.data
  (:require
   [com.stuartsierra.component :refer [Lifecycle]]
   [crux.api :refer [entity db submit-tx start-node q]]
   [clj-uuid :refer [v1]]))

(defn map-client
  [name address email phone user-capacity]
  {:crux.db/id (v1)
   :tag [:client :main]
   :name name
   :address address
   :email email
   :phone phone
   :user-capacity user-capacity})

(defn get-all-clients [conn]
  (q (db conn)
     '{:find [p1]
       :where [[p1 :tag :client]]})) ;; need to correct

(defn get-client-by-name [conn name]
  (q (db conn)
     '{:find [p1]
       :where [[p1 :tag :client]
               [p1 :name name]]})) ;; need to correct 

(defn put-client
  [conn
   {:keys [name address email
           phone user-capacity]}]
  (submit-tx
   conn
   [[:crux.tx/put
     (map-client name address email
                 phone user-capacity)]]))

(defn map-counter [num]
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
     (map-counter 0)]]))

(defn update-counter [conn f]
  (let [bal (:num (get-counter conn))]
    (submit-tx
     conn
     [[:crux.tx/put
       (map-counter (f bal))]])))

(defrecord Db [options conn]
  Lifecycle
  (start [this]
    (let [conn (start-node {})]
      (init-counter conn)
      (assoc this :conn conn)))
  (stop [this]
    (when conn (.close conn))
    (assoc this :conn nil)))
