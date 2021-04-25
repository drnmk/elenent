(ns elenent.server.route
  (:require
   [compojure.core :as c]
   [compojure.route :as r]
   [elenent.comps.contract.control :as contract]))

(c/defroutes routes
  (c/GET "/contracts" [] contract/get-futures)
  (c/GET "/contracts-add" [] contract/add-futures)
  (c/GET "/contracts-view-add" [] contract/get-futures-add-one)
  (c/POST "/contracts" [] contract/add-futures)
  (r/not-found "Not Found"))
