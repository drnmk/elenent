(ns elenent.comps.future.control
  (:require
   [elenent.layout.control :as layout]
   [elenent.comps.future.data :refer :all]
   [elenent.comps.future.view :refer :all]))

(defn get-futures [req]
  (let [data (vec (read-futures))]
    {:status 200
     :header {}
     :body (layout/page-frame
            (view-futures-table data))}))

(defn add-futures [req]
  {:status 200
   :header {}
   :body (layout/page-frame
          (view-new-form))})

(defn get-futures-add-one [req]
  (let [data (vec (read-futures))]
    {:status 200
     :header {}
     :body (layout/page-frame
            (view-futures-table data)
            (view-future-form))}))
