(ns elenent.db
  (:require
   [java-time :as jtm]
   [clojure.java.io :as io]
   [clj-uuid :as uid]
   [crux.api :as crux]))


;;;;;;;;;;;;;;;;
;;;; helper ;;;;
;;;;;;;;;;;;;;;;

(defn str->uuid [s]
  (java.util.UUID/fromString s))


;;;;;;;;;;;;;;
;;;; base ;;;;
;;;;;;;;;;;;;;

(def cn 
  (crux/start-node {}))

(defn write-position [name kind desc]
  (crux/submit-tx
   cn
   [[:crux.tx/put
     {:crux.db/id (uid/v1)
      :position/name name
      :position/kind kind
      :position/desc desc}]]))

(defn read-positions [] ;; db, user/session
  (crux/q
   (crux/db cn) ;; this part passed as argument 
   '{:find [id name kind desc]
     :where [[e :position/kind :future]
             [e :crux.db/id id]
             [e :position/name name]
             [e :position/kind kind]
             [e :position/desc desc]]}))

(-> 
 (read-positions)
 first
 type
 )

(def first-id 
  (ffirst 
   (crux/q
    (crux/db cn)
    '{:find [id]
      :where [[e :position/kind :future]
              [e :crux.db/id id]]})))

first-id

(crux/entity
 (crux/db cn)
 first-id
 )
