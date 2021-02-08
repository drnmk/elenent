(ns elenent.db
  (:require
   [java-time :as jtm]
   [next.jdbc :as jdb]
   [clj-uuid :as uid]
   [hugsql.core :as hgs]
   [hugsql.adapter.next-jdbc :as hnj]
   [honeysql.core :as hsc]
   [honeysql.helpers :as hsh]
   [elenent.schema :as scm]))


;;;;;;;;;;;;;;;;
;;;; helper ;;;;
;;;;;;;;;;;;;;;;

(defn str->uuid [s]
  (java.util.UUID/fromString s))


;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; db initialization ;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def db
  {:dbtype "postgresql"
   :dbname "elenent_db"
   :host "localhost"
   :user "elenent_adm"
   :password "el1234"})

(def ds
  (jdb/get-datasource db))

(def sql-files
  "all sql files in the project."
  ["elenent/base.sql"
   "elenent/tool.sql"])

(for [file sql-files]
  ;; load sql files on the runtime.
  (do 
    (hgs/def-sqlvec-fns file 
      {:adapter (hnj/hugsql-adapter-next-jdbc)})
    (hgs/def-db-fns file
      {:adapter (hnj/hugsql-adapter-next-jdbc)})))

;; commands to load/unload base
;; not needed after initiation 
;; (sql-create-base-sqlvec)
;; (sql-create-base db)
;; (sql-remove-base-sqlvec)
;; (sql-remove-base db) ;; careul!!



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; interface for trait ;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn trait-be-unique? [tkey]
  (let [sql (hsc/format
             {:select [:%count.*]
              :from [:trait]
              :where [:and
                      [:= :key tkey]]})
        raw (jdb/execute! ds sql)
        head-count (-> raw first :count)
        unique? (if (= head-count 0) true false)]
    unique?))
;; (trait-be-unique? :client/email)

(defn load-new-traits [traits]
  "load all newly added traits in schema."
  (for [trait traits]
    (let [tkey (str (key trait))
          tvals (val trait)
          uni (name (get tvals 0))
          cardi (name (get tvals 1))
          vtype (name (get tvals 2))
          ]
      (when (trait-be-unique? tkey)
        (jdb/execute!
         ds
         (-> (hsh/insert-into :trait)
             (hsh/columns :key :uni :cardi :vtype :stamp)
             (hsh/values [[tkey uni cardi vtype :default]])
             hsc/format)

         )))))
;; only run when new traits 
;; (load-new-traits scm/traits)

(defn get-all-traits []
  (let [sql (hsc/format
             {:select [:*]
              :from [:trait]})
        data (jdb/execute! ds sql)]
    data))
;; (get-all-traits)


;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; interface for atom ;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-all-atoms []
  (let [sql (hsc/format
             {:select [:*]
              :from [[:atom :a]]
              :left-join [[:trait :t] [:= :a.tkey :t.key]]})
        data (jdb/execute! ds sql)]
    data))
(get-all-atoms)

(defn get-atom-by-id [atom-id]
  (let [sql (hsc/format
             {:select [:*]
              :from [[:atom :a]]
              :left-join [[:trait :t] [:= :a.tkey :t.key]]
              :where [[:= :a.id atom-id]]}) 
        raw (jdb/execute! ds sql)]
    raw))

(defn atom-value-be-universally-unique? [tkey value]
  (let [sql (hsc/format
             {:select [:%count.*]
              :from [:atom]
              :where [:and
                      [:= :tkey tkey]
                      [:= :value value]]})
        raw (jdb/execute! ds sql)
        head-count (-> raw first :count)
        unique? (if (= head-count 0) true false)]
    unique?))
;; (will-value-be-unique? "33")

(defn create-ent []
  (uid/v1))

(defn create-atom [ent tkey value]
  (let [id (uid/v1)]
    (jdb/execute!
     ds
     (-> (hsh/insert-into :atom)
         (hsh/columns :id :ent :tkey :val :rid :valid :stamp)
         (hsh/values [[id ent tkey value id true :default]])
         hsc/format))))
;; (create-atom (create-ent) (str :rate/ticker) "ESH2")

(defn retract-atom-by-id [rid]
  (let [id (uid/v1)
        ent (-> (jdb/execute! ds (hsc/format {:select [:ent]
                                              :from [:atom]
                                              :where [:= :id rid]}))
                first :atom/ent)
        tkey (-> (jdb/execute! ds (hsc/format {:select [:tkey]
                                               :from [:atom]
                                               :where [:= :id rid]}))
                 first :atom/tkey)
        val (-> (jdb/execute! ds (hsc/format {:select [:val]
                                              :from [:atom]
                                              :where [:= :id rid]}))
                first :atom/val)]
    (jdb/execute!
     ds
     (-> (hsh/insert-into :atom)
         (hsh/columns :id :ent :tkey :val :rid :valid :stamp)
         (hsh/values [[id ent tkey val rid false :default]])
         hsc/format))))

(defn get-atoms []
  (sql-get-atoms db))
;; (get-atoms)

(defn get-atoms-by-ent [ent]
  (sql-get-atoms-by-ent db {:ent ent}))
;; (get-atoms-by-ent (str->uuid "d8c49a00-6a16-11eb-a691-2e6a399ed2c1"))
