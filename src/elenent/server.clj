(ns elenent.server
  (:require
   [com.stuartsierra.component :as component]
   [ring.adapter.jetty :as j]
   [ring.middleware.resource :as s]
   [ring.middleware.params :as p]   
   [ring.middleware.file-info :as f]
   [compojure.core :refer [defroutes]]
   [compojure.route :as r]
   ))

(defroutes routes
  ;; (c/GET "/contracts" [] contract/get-futures)
  ;; (c/GET "/contracts-add" [] contract/add-futures)
  ;; (c/GET "/contracts-view-add" [] contract/get-futures-add-one)
  ;; (c/POST "/contracts" [] contract/add-futures)
  (r/not-found "Not Found"))

(def app
  (->> "static"
       (s/wrap-resource 
        (p/wrap-params routes))
       (f/wrap-file-info)))

(defn make-handler [db]
  (fn [request]
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body "aaa"}))

(defrecord Server [options db server]
  component/Lifecycle
  (start [this]
    (let [server (j/run-jetty
                  (make-handler db)
                  options)]
      (assoc this :server server)))
  (stop [this]
    (when server (.stop server))
    (assoc this :server nil)))
