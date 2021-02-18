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

(write-position "abc111future"
                :future
                "to hedge interest rate hike")

(defn read-positions []
  (crux/q
   (crux/db cn)
   '{:find [name kind desc]
     :where [[e :position/kind :future]
             [e :position/name name]
             [e :position/kind kind]
             [e :position/desc desc]]}))

(for [p (into [] (read-positions))]
  (println (last p))
  )
