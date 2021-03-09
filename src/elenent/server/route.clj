(ns elenent.server.route
  (:require
   [compojure.core :as c]
   [compojure.route :as r]
   [elenent.comps.future.control :as future]))

(c/defroutes routes
  (c/GET "/futures" [] future/get-futures)
  (c/GET "/futures-add" [] future/add-futures)
  (c/GET "/futures-view-add" [] future/get-futures-add-one)
  (c/POST "/futures" [] future/add-futures)
  (r/not-found "Not Found"))
