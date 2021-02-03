(ns elenent.db
  (:require
   [java-time :as jtm]
   [next.jdbc :as jdb]   
   [hugsql.core :as hgs]
   [hugsql.adapter.next-jdbc :as hnj]))

(def db
  {:dbtype "postgresql"
   :dbname "elenent_db"
   :host "localhost"
   :user "elenent_adm"
   :password "el1234"})

(defn load-sql []
  (do 
    (hgs/def-sqlvec-fns "elenent/schema.sql"
      {:adapter (hnj/hugsql-adapter-next-jdbc)})
    (hgs/def-db-fns "elenent/schema.sql"
      {:adapter (hnj/hugsql-adapter-next-jdbc)})
    (hgs/def-sqlvec-fns "elenent/tool.sql"
      {:adapter (hnj/hugsql-adapter-next-jdbc)})
    (hgs/def-db-fns "elenent/tool.sql"
      {:adapter (hnj/hugsql-adapter-next-jdbc)})))

(load-sql)

(comment
  "remove all tables from db."
  "dev purpose only"
  (drop-table-all db))

(defn define-tables []
  (do 
    (define-log db)
    (define-client db)
    (define-client-info db)))

;; (define-tables)

(defn create-client [client-name client-email] 
  (let [cid (-> (create-client-get-id
                 db {:branch (name :client_info)})
                first :id)
        lid (-> (create-log-get-id
                 db {:by 0})
                first :id)]
    (add-client-info
     db {:client_id cid 
         :name client-name
         :email client-email
         :log_id lid})))

(defn update-client [client-id client-name client-email] 
  (let [log-id (-> (create-log-get-id
                 db {:by client-id})
                first :id)]
    (add-client-info
     db {:client_id client-id  
         :name client-name
         :email client-email 
         :log_id log-id})))

(defn get-client [client-id]
  (get-client-by-id db {:cid client-id}))

