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

(do
  (hgs/def-sqlvec-fns "elenent/base.sql"
    {:adapter (hnj/hugsql-adapter-next-jdbc)})
  (hgs/def-db-fns "elenent/base.sql"
    {:adapter (hnj/hugsql-adapter-next-jdbc)})
    (hgs/def-sqlvec-fns "elenent/tool.sql"
    {:adapter (hnj/hugsql-adapter-next-jdbc)})
  (hgs/def-db-fns "elenent/tool.sql"
    {:adapter (hnj/hugsql-adapter-next-jdbc)}))

(defn create-tables []
  (do
    (create-table-trait db)
    (create-table-vint db)
    (create-table-vfloat db)
    (create-table-vtext db)
    (create-table-vdate db)
    (create-table-vstamp db)
    (create-table-entity db)
    (create-table-atom db)
    ))
(create-tables)

(defn remove-tables []
  (drop-table-all db))
(remove-tables)

(defn load-traits [traits]
  (for [trait traits]
    (let [sbj  (namespace (key trait))
          att (name (key trait))
          uni (get (val trait) 0)
          sng (get (val trait) 1)
          vtp (name (get (val trait) 2))]
      (when (> 1
               (-> (select-count-existing-trait
                    db {:subject sbj
                        :attribute att})
                   first :count))
        (insert-into-trait
         db {:subject sbj
             :attribute att
             :uniq uni 
             :single sng
             :vtype vtp})))))
(load-traits scm/traits)

(defn create-value-get-id [trait value]
  (case (get (trait scm/traits) 2)
    :text (-> (insert-into-vtext db {:val value}) first :id)))

(defn create-atom [trait value]
  (let [entity-id (-> (insert-into-entity db) first :id)
        trait-id (-> (select-find-trait-id db {:subject (namespace trait)
                                               :attribute (name trait)})
                     first :id)
        atom-id 1 ;;(-> (select-get-available-atom-id db) first :id)
        value-id (create-value-get-id trait value)
        ]
    (insert-into-atom
     db {:id atom-id 
         :entity_id entity-id
         :trait_id trait-id
         :value_id value-id 
         :ref_id atom-id 
         :valid true})))

(def trait :client/short-name)
(def value "Darren Z")
(create-atom :client/short-name "Darren, LLC")

(select-show-full-atom-by-id db {:id 1})




























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

