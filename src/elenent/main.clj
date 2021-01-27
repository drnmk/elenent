(ns elenent.main
  (:require [elenent.server :as srv]))

(defn run-dev [args]
  (srv/run-dev 8080))

(defn run-prod [args]
  (srv/run-prod 8080))
