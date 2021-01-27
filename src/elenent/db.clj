(ns elenent.db
  (:require
   [datomic.api :as d]
   [elenent.schema]))

(def db-uri "datomic:mem://ex-db-1")

(d/create-database db-uri)

(def conn (d/connect db-uri))

;; transact schema into db
(d/transact conn elenent.schema/schema)

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



