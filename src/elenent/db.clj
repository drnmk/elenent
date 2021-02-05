(ns elenent.db
  (:require
   [java-time :as jtm]
   [next.jdbc :as jdb]   
   [hugsql.core :as hgs]
   [hugsql.adapter.next-jdbc :as hnj]
   [elenent.schema :as scm]))

(def db
  {:dbtype "postgresql"
   :dbname "elenent_db"
   :host "localhost"
   :user "elenent_adm"
   :password "el1234"})

(def sql-files
  "all sql files in the project."
  ["elenent/base.sql"
   "elenent/tool.sql"])
;; load sql files on the runtime.
(for [sf sql-files]
  (do 
    (hgs/def-sqlvec-fns sf
      {:adapter (hnj/hugsql-adapter-next-jdbc)})
    (hgs/def-db-fns sf
      {:adapter (hnj/hugsql-adapter-next-jdbc)})))

;; commands to load/unload base
;; not needed after initiation 
;; (sql-create-base-sqlvec)
;; (sql-create-base db)
;; (sql-remove-base-sqlvec)
;; (sql-remove-base db) ;; careul!!

(defn find-trait-id [trait-key]
  "expects trait-key such as :client-email"
  (-> (sql-find-trait-id
       db {:subject (namespace trait-key) 
           :attribute (name trait-key)})
      first :id))
;; e.g. (find-trait-id :client/email)

(defn trait-unique? [trait-key]
  "checks if trait table has zero item of the key."
  (let [cnt (-> (sql-count-trait
                 db {:subject (namespace trait-key) 
                     :attribute (name trait-key)})
                first :count)]
    (if (= cnt 0)
      true
      false)))
;; e.g. (trait-unique? :client/email)

(defn load-new-traits [traits]
  "load all newly added traits in schema."
  (for [trait traits]
    (let [k (key trait)
          v (val trait)
          sbj (namespace k)
          att (name k)
          uni (get v 0)
          sng (get v 1)
          vtp (name (get v 2))]
      (when (trait-unique? k)
        (sql-load-trait
         db {:subject sbj
             :attribute att
             :uniq uni 
             :single sng
             :vtype vtp})))))
;; only run when new traits 
;; (load-new-traits scm/traits)































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

