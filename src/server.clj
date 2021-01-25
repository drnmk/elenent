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
            [hiccup.core :as hcc :refer [html h]]
            [datomic.api :as d]
            ))

(def db-uri "datomic:mem://ex-db-1")

(d/create-database db-uri)

(def conn (d/connect db-uri))

(def schema
  [{:db/ident :client/name
    :db/valueType :db.type/string
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :client/email
    :db/valueType :db.type/string
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :client/asset
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many}
   {:db/ident :client/contract
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/many}
   {:db/ident :asset/breed
    :db/valueType :db.type/keyword
    :db/cardinality :db.cardinality/one}
   {:db/ident :asset/name
    :db/valueType :db.type/string
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :contract/name
    :db/valueType :db.type/string
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :contract/asset
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one}
   ])

;; transact schema into db
(d/transact conn schema)

(def sample-assets
  [{:asset/breed :corporate-bond :asset/name "msft-5-year-bond"}
   {:asset/breed :corporate-bond :asset/name "amazon-10-year-bond"}
   {:asset/breed :common-stock :asset/name "tsla-common-stock"}
   {:asset/breed :common-stock :asset/name "tsla-preferred-stock"}
   {:asset/breed :etf :asset/name "voo"}
   {:asset/breed :etf :asset/name "jets"}
   {:asset/breed :cryptocurrency :asset/name "btc"}
   {:asset/breed :cryptocurrency :asset/name "eth"}
   {:asset/breed :real-estate :asset/name "100 broad ave, nyc"}
   {:asset/breed :real-estate :asset/name "tampa 200 land"}])

;; enter sample assets
(d/transact conn sample-assets)

(def assets-read-from-db 
  (d/q '[:find ?e ?b ?n
         :where
         [?e :asset/breed ?b]
         [?e :asset/name ?n]]
       (d/db conn)))

(println (second (rest (first assets-read-from-db))))

(defn layout [components]
  (hcp/html5 {:lang :en}
             [:head
              [:title "Elenent!"]
              [:meta {:name :viewport
                      :content "width=device-width, initial-scale=1.0"}]
              [:link {:href "/bootstrap/css/bootstrap.min.css"
                      :rel :stylesheet}]]
             [:body
              [:div components]
              [:script {:src "http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"}]
              [:script {:src "/bootstrap/js/bootstrap.min.js"}]]))

(defn items-page [assets]
  [:div.container
   [:h1 "items to test hiccup and datomic:"]
   [:table.table 
    [:tr
     [:th "datomic id"]
     [:th "attribute"]
     [:th "value"]]
    (for [asset assets]
      [:tr
       [:td (first asset)]
       [:td (second asset)]
       [:td (last asset)]])]])

(defn greet-page []
  [:div.container
   [:h1 "hello, this is elenent app in contstruction."]
   [:h2 "I practiced hiccup and datomic in report page"]
   [:a {:href "/reports"} "Go to reports page!"]])

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
