(ns elenent.pages
  (:require
   [hiccup.page :as hcp :refer [html5]]
   [hiccup.core :as hcc :refer [html h]]))

(defn items-page [assets]
  [:div.container
   [:h1 "items to test hiccup and datomic:"]
   [:table.table 
    [:tr
     [:th "datomic id"]
     [:th "attribute"]
     [:th "value"]]
    (for [asset assets]
      [:tr
       [:td (first asset)]
       [:td (second asset)]
       [:td (last asset)]])]])

(defn greet-page []
  [:div.container
   [:h1 "hello, this is elenent app in contstruction."]
   [:h2 "I practiced hiccup and datomic in report page"]
   [:a {:href "/reports"} "Go to reports page!"]])
