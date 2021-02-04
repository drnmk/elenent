(ns elenent.schema
  (:require
   [java-time :as jtm]))

(def traits
  {:client/legal-name [true true :text]
   :client/short-name [true true :text]
   :client/email [true true :text]
   :person/first-name [true true :text]
   :person/last-name [true true :text]})
