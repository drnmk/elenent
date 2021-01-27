(ns elenent.ui
  (:require
   [hiccup.page :as hcp :refer [html5]]
   [hiccup.core :as hcc :refer [html h]]))


 (defn navbar []
   [:div
    [:p "navigation bar under construction."]])

(defn layout [components]
  (hcp/html5 {:lang :en}
             [:head
              [:title "Elenent!"]
              [:meta {:name :viewport
                      :content "width=device-width, initial-scale=1.0"}]
              [:link {:href "/bootstrap/css/bootstrap.min.css"
                      :rel :stylesheet}]]
             [:body
              [:div
               navbar
               components]
              [:script {:src "http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"}]
              [:script {:src "/bootstrap/js/bootstrap.min.js"}]]))

(defn greet-page []
  [:div.container
   [:h1 "hello, this is elenent app in contstruction."]
   [:h2 "I practiced hiccup and datomic in report page"]
   [:a {:href "/reports"} "Go to reports page!"]])

(defn asset-treasurynotes [items]
  [:div.container
   [:h1 "Treasury Notes"
    [:table.table
     [:tr
      [:th "Ticker"]
      [:th "Multiplier"]
      ]]
    (for [item items]
      [:tr
       [:td (:ticker item)]
       [:td (:multiplier item)]])]])



(comment 
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



  )
