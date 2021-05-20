(ns elenent.server
  (:require
   [com.stuartsierra.component :refer [Lifecycle]]
   [ring.adapter.jetty :refer [run-jetty]]
   [compojure.core :refer [routes GET POST DELETE]]
   [elenent.db :as eldb]
   [elenent.layout :as ellayout]
   [elenent.page :as elpage]))

(defn counter-handler [db]
  (fn [request]
    (let [_ (eldb/update-counter (:conn db) inc)]
      {:status 200
       :headers {"Content-Type" "text/plain"}
       :body (str "the counter balance is "
                  (-> (eldb/get-counter (:conn db))
                      :num))})))

(def routing
  (routes 
   (GET "/" [] "hello")
   (GET "/counter" [] (counter-handler))
   (GET "/login/:id" [id] (str "login id is " id))
   (GET "/dashboard" [] "dashboard")
   (GET "/deal" [] (ellayout/basic (elpage/deal-table)))
   (GET "/position" [] "position")
   (GET "/transaction" [] "transaction")))

(def app-sys routing)

(defrecord Server [options db instance]
  Lifecycle
  (start [this]
    (let [instance (run-jetty app-sys options)]
      (assoc this :instance instance)))
  (stop [this]
    (when instance (.stop instance))
    (assoc this :instance nil)))
