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
   [datomic.api :as d]
   ))

(defn greet [req]
  (do 
    (println (str req))
    {:status 200
     :headers {}
     :body (layout (greet-page))}))

(defn get-reports [req]
  {:status 200
   :headers {}
   :body (layout (items-page assets-read-from-db))})

(defn get-assets [req]
  {:status 200
   :headers {}
   :body (str "this is asset get. "
              "you asked "
              (get-in req [:route-params :id]))})

(def unfound "Not Found")

(cc/defroutes routes
  (cc/GET "/" [] greet)
  (cc/GET "/assets/:id" [] get-assets)
  (cc/GET "/contracts" [] greet)
  (cc/GET "/reports" [] get-reports)
  (cr/not-found unfound))

(def static-path "static")

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
