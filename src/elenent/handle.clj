(ns elenent.handle
  (:require
   [elenent.view :as v]
   [elenent.db :as db]))

(defn get-positions [rq]
  (let [data (into [] (db/read-positions))]   
    {:status 200
     :headers {}
     :body (v/layout
            (v/positions data)
            (v/position-form))}))

(defn post-positions [rq]
  (let [name (get-in rq [:params "name"])
        kind :future
        desc (get-in rq [:params "desc"])
        item (db/write-position name kind desc)]
    {:status 302
     :headers {"Location" "/positions"}
     :body ""}))
