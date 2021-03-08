(ns elenent.main
  (:require
   [elenent.server.control :as server]))

(def port 8080)

(defn run-dev [args]
  (server/run port :dev))

(defn run-prod [args]
  (server/run port :prod))
