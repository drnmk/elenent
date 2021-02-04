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
  ;; ! consider moving to top level
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
(comment
  {:client-name "foo"
   :client-email "a@a"}
  ;; use namespaced keys
  {:client/name "foo"
   :client/email "a@a"}
  ;; is equal to
  #:client{:name "foo" :email "email"} ;; most conventional typical standardish way

  (keys #:client{:name "foo" :email "email"})
  ;; -> (:client/name :client/email)

  ;;see:
  "https://clojure.org/guides/destructuring#_associative_destructuring"


  )

(defn create-client [{:client/keys [email]
                      client-name :client/name}]
  (let [cid (-> (create-client-get-id
                 db {:branch (name :client_info)})
                first :id)
        lid (-> (create-log-get-id
                 db {:by 0})
                first :id)]
    (add-client-info
     db {:client_id cid
         :name name
         :email email
         :log_id lid})))

;; use maps instead of positional arguments

(defn update-client [client-id client-name client-email]
  (let [log-id (-> (create-log-get-id
                 db {:by client-id})
                first :id)]
    (add-client-info
     db {:client_id client-id
         :name client-name
         :email client-email
         :log_id log-id})))

(comment
  {:method :create-client
   :client/name "foo"
   :client/email "a@a.a"}

  {:method :update-client
   :client/name "foo"
   :client/email "a@a.a"}

  (defmuli ^:private dispatch* :method)

  (defmethod dispatch* :create-client
    [{:client/keys [email]
      client-name :client/name}]
    (let [cid (-> (create-client-get-id
                   db {:branch (name :client_info)})
                  first :id)
          lid (-> (create-log-get-id
                   db {:by 0})
                  first :id)]
      (add-client-info
       db {:client_id cid
           :name name
           :email email
           :log_id lid})))

  (defmethod dispatch* :update-client
    [{:client/keys [email]
      client-name :client/name}]
    ;; ...
    )


  (defn dispatch [request]
    (log/info request)
    ;; lateron for example check authentication cookies here
    (dispatch* request))


  )

(defn get-client [client-id]
  (get-client-by-id db {:cid client-id}))
