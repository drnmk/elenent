(ns elenent.server.control
  (:require
   [ring.adapter.jetty :as j]
   [ring.middleware.resource :as s]
   [ring.middleware.reload :as l]
   [ring.middleware.params :as p]   
   [ring.middleware.file-info :as f]   
   [elenent.server.tool :refer :all]
   [elenent.server.route :refer :all]))

(def static-path "static")

(def app
  (->> static-path
       (s/wrap-resource 
        (p/wrap-params routes))
       (f/wrap-file-info)))

(defn run [port purpose]
  (do 
    (serve-message port)
    (j/run-jetty (case purpose
                    :dev (l/wrap-reload #'app)
                    :prod app)
                  {:port (Integer. port)})))
