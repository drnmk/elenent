(ns elenent.server
  (:require
   [com.stuartsierra.component :refer [Lifecycle]]
   [ring.adapter.jetty :refer [run-jetty]]
   [compojure.core :refer [routes GET POST DELETE]]
   [elenent.data :as data]
   [elenent.layout :as layout]
   [elenent.page :as page]))

(defn counter-handler [db]
  (fn [request]
    (let [_ (data/update-counter (:conn db) inc)]
      {:status 200
       :headers {"Content-Type" "text/plain"}
       :body (str "the counter balance is "
                  (-> (data/get-counter (:conn db))
                      :num))})))

(defn routing [db]
  (routes 
   (GET "/" [] (layout/frame (page/index)))
   (GET "/counter" [] (counter-handler db))
   (GET "/signup" [] (layout/frame (page/sign-up)))
   (GET "/signin" [] (layout/frame (page/sign-in)))
   (GET "/login/:id" [id] (str "login id is " id))
   (GET "/dashboard" [] "dashboard")
   (GET "/deal" [] (layout/frame (page/deal-table)))
   (GET "/position" [] "position")
   (GET "/transaction" [] "transaction")))

(defrecord Server [options db instance]
  Lifecycle
  (start [this]
    (let [instance (run-jetty (routing db) options)]
      (assoc this :instance instance)))
  (stop [this]
    (when instance (.stop instance))
    (assoc this :instance nil)))
