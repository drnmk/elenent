(ns elenent.main
  (:require [elenent.server :as srv]))

(def port 8080)

(defn run-dev [args]
  (srv/run port :dev))

(defn run-prod [args]
  (srv/run port :prod))
