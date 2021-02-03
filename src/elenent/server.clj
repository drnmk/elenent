(ns elenent.server
  (:require
   [ring.adapter.jetty :as jt]
   [ring.middleware.resource :as rs]
   [ring.middleware.reload :as rmr :refer [wrap-reload]]
   [ring.middleware.params :as rmp :refer [wrap-params]]
   [ring.middleware.resource :as rms :refer [wrap-resource]]
   [ring.middleware.file-info :as rmf :refer [wrap-file-info]]
   [compojure.core :as cc]
   [compojure.route :as cr]
   [hiccup.page :as hcp :refer [html5]]
   [hiccup.core :as hcc :refer [html h]]
   [elenent.ui :as ui]
   [elenent.db :as db]))

(def unfound "Not Found")
(def static-path "static")

(defn greet [req]
  (do 
    (println (str req))
    {:status 200
     :headers {}
     :body (ui/layout (ui/greet-page))}))

(defn handle-client-sign-up [req]
  {:status 200
   :headers {}
   :body (ui/layout
          (ui/sign-up-client-comp))})

(defn handle-create-client [req]
  (let [name (get-in req [:params "name"])
        email (get-in req [:params "email"])
        client-id (db/create-client name email)])
  {:status 302
   :headers {"Location" "/clients"}
   :body ""})

(cc/defroutes routes
  (cc/GET "/" [] greet)
  (cc/GET "/client-sign-up" [] handle-client-sign-up)
  (cc/POST "/clients" [] handle-create-client)
  (cr/not-found unfound))

(def app
  (rmf/wrap-file-info 
   (rms/wrap-resource 
    (rmp/wrap-params routes)
    "static")))

(defn run-dev [port]
  (println "Development Server"
           "is Running at"
           port "...")
  (jt/run-jetty (rmr/wrap-reload #'app)
                {:port (Integer. port)}))

(defn run-prod [port]
  (println "Production Server"
           "is Running at"
           port "...")
  (jt/run-jetty app 
                {:port (Integer. port)}))
