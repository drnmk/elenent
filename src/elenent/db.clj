(ns elenent.db
  (:require
   [java-time :as jtm]
   [next.jdbc :as jdb]   
   [honeysql.core :as hsq]
   [honeysql.helpers :as hsh]))

(def db
  {:dbtype "postgresql"
   :dbname "elenent_db"
   :host "localhost"
   :user "elenent_adm"
   :password "el1234"})

(def ds
  (jdb/get-datasource db))

;; client

(jdb/execute!
 ds
 (hsq/format
  {:insert-into :client
   :columns [:id :created_at]
   :values [[:default :default]]}))

(jdb/execute!
 ds
 (hsq/format
  {:select [:*]
   :from [:client]}))


;; client-info

(jdb/execute!
 ds
 (hsq/format
  {:insert-into :client_info
   :columns [:id :client_id :name :email :entered_at]
   :values [[:default 1 "ABC Corp." "abc@zzz.com" :default]]}))

(jdb/execute!
 ds
 (hsq/format
  {:select [:*]
   :from [:client_info]}))

;; person 
(jdb/execute!
 ds
 (hsq/format
  {:insert-into :person
   :columns [:id :client_id :created_at]
   :values [[:default 1 :default]]}))

(jdb/execute!
 ds
 (hsq/format
  {:select [:*]
   :from [:person]}))

;; person_info

(jdb/execute!
 ds
 (hsq/format
  {:insert-into :person_info
   :columns [:id :person_id :name :email :phone :entered_at]
   :values [[:default 1 "Janet Cole" "janet_cole@zzz.com" "1234-1234" :default]]}))

(jdb/execute!
 ds
 (hsq/format
  {:select [:*]
   :from [:person_info]}))


;;

(jdb/execute!
 ds
 (hsq/format
  {:insert-into :log
   :columns [:id :person_id :created_at]
   :values [[:default 1 (hsq/call :now)]]}))

(jdb/execute!
 ds
 (hsq/format
  {:select [:*]
   :from [:log]}))



(jdb/execute!
 ds
 (hsq/format
  {:select [:*]
   :from [:security]}))

(jdb/execute!
 ds
 (hsq/format
  {:insert-into :security
   :columns [:id :branch :log_id]
   :values [[:default "security_treasury_note" 2]]}))

(jdb/execute!
 ds
 (hsq/format
  {:select [:*]
   :from [:security_treasury_note]}))

(def sample-tn-1
  [:default
   1
   "vxab"
   "treasury 10y"
   100000
   (jtm/local-date 2021 1 21)
   1
   nil
   true])

(def sample-tn-2
  [:default
   1
   "vxab"
   "treasury 10y"
   100000
   (jtm/local-date 2021 1 21)
   2
   1
   false])

(def sample-tn-3
  [:default
   1
   "vxab"
   "treasury 10y"
   200000
   (jtm/local-date 2021 1 22)
   3
   nil
   true])


(jdb/execute!
 ds
 (hsq/format
  {:insert-into :security_treasury_note
   :columns [:id
             :security_id
             :ticker
             :description
             :multiplier
             :maturity
             :log
             :retract_id
             :op]
   :values [sample-tn-1
            sample-tn-2
            sample-tn-3]}))



