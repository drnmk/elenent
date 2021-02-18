(ns elenent.server
  (:require
   [ring.adapter.jetty :as jt]
   [ring.middleware.resource :as rs]
   [ring.middleware.reload :as rmr]
   [ring.middleware.params :as rmp]
   [ring.middleware.resource :as rms]
   [ring.middleware.file-info :as rmf]
   [compojure.core :as cc]
   [compojure.route :as cr]
   [hiccup.page :as hcp]
   [hiccup.core :as hcc]
   [elenent.handle :as hnd]
   [elenent.db :as db]))

(def unfound "Not Found")
(def static-path "static")


(cc/defroutes routes
  (cc/GET "/positions" [] hnd/get-positions)
  (cc/POST "/positions" [] hnd/post-positions)
  (cr/not-found unfound))

(def app
  (rmf/wrap-file-info 
   (rms/wrap-resource 
    (rmp/wrap-params routes)
    "static")))

(defn serve-message [port]
  (str "Development Server "
       "is Running at "
       port "..."))

(defn run-dev [port]
  (do 
    (serve-message port)
    (jt/run-jetty (rmr/wrap-reload #'app)
                  {:port (Integer. port)})))

(defn run-prod [port]
  (do
    (serve-message port)
    (jt/run-jetty app 
                  {:port (Integer. port)})))
