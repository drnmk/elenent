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
   [elenent.db :as db]
   ))

(defn greet [req]
  (do 
    (println (str req))
    {:status 200
     :headers {}
     :body (ui/layout (ui/greet-page))

     }))

(comment 
  (defn get-reports [req]
    {:status 200
     :headers {}
     :body (elpg/layout (items-page assets-read-from-db))})

  (defn get-assets [req]
    {:status 200
     :headers {}
     :body (str "this is asset get. "
                "you asked "
                (get-in req [:route-params :id]))}))

(def unfound "Not Found")

(defn handle-treasurynote [req]
  {:statue 200
   :headers {}
   :body (ui/layout
          (ui/asset-treasurynotes
           [{:ticker "abc" :multiplier 100000}
            {:ticker "def" :multiplier 200000}]
                      ))})

(cc/defroutes routes
  (cc/GET "/" [] greet)
  (cc/GET "/treasurynotes" [] handle-treasurynote)
  ;;  (cc/GET "/assets/:id" [] get-assets)
;;  (cc/GET "/contracts" [] greet)
;;  (cc/GET "/reports" [] get-reports)
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
