(ns server
  (:require [ring.adapter.jetty :as jt]
            [ring.middleware.resource :as rs]
            [ring.middleware.reload :as rmr :refer [wrap-reload]]
            [ring.middleware.params :as rmp :refer [wrap-params]]
            [ring.middleware.resource :as rms :refer [wrap-resource]]
            [ring.middleware.file-info :as rmf :refer [wrap-file-info]]
            [compojure.core :as cc]
            [compojure.route :as cr]
            [hiccup.page :as hcp :refer [html5]]
            [hiccup.core :as hcc :refer [html h]]))

(defn items-page []
  (hcp/html5 {:lang :en}
             [:head
              [:title "Elenent!"]
              [:meta {:name :viewport
                      :content "width=device-width, initial-scale=1.0"}]
              [:link {:href "/bootstrap/css/bootstrap.min.css"
                      :rel :stylesheet}]]
             [:body
              [:div.container
               [:h1 "my items:"]
               [:p "dog"]
               [:p "cat"]
               [:p "cow"]]
              [:script {:src "http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"}]
              [:script {:src "/bootstrap/js/bootstrap.min.js"}]]))

(defn greet [req]
  (do 
    (println (str req))
    {:status 200
     :headers {}
     :body "<h1>Hi all, this is elenent in construction.</h1>"}))

(defn get-reports [req]
  {:status 200
   :headers {}
   :body (items-page)})

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
