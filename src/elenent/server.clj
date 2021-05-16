(ns elenent.server
  (:require
   [com.stuartsierra.component :as component]
   [ring.adapter.jetty :as jet]
   [elenent.db :as eldb]))

(defn make-handler [db]
  (fn [request]
    (let [_ (eldb/update-counter (:conn db) inc)]
      {:status 200
       :headers {"Content-Type" "text/plain"}
       :body (str "the counter balance is "
                  (-> (eldb/get-counter (:conn db))
                      :num))})))

(defrecord Server [options db instance]
  component/Lifecycle
  (start [this]
    (let [instance (jet/run-jetty
                  (make-handler db)
                  options)]
      (assoc this :instance instance)))
  (stop [this]
    (when instance (.stop instance))
    (assoc this :instance nil)))
