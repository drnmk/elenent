(ns elenent.comps.contract.control
  (:require
   [elenent.layout.frame :refer [page-frame]]
   [elenent.comps.contract.data :refer :all]
   [elenent.comps.contract.view :refer :all]))

(defn get-futures [req]
  (let [data (vec (read-futures))]
    {:status 200
     :header {}
     :body (page-frame
            (view-futures-table data))}))

(defn add-futures [req]
  {:status 200
   :header {}
   :body (page-frame
          (view-new-form))})

(defn get-futures-add-one [req]
  (let [data (vec (read-futures))]
    {:status 200
     :header {}
     :body (page-frame
            (view-futures-table data)
            (view-future-form))}))
