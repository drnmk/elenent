(ns elenent.layout
  (:require
   [hiccup.page :as hcp :refer [html5]]
   [hiccup.core :as hcc :refer [html h]]))

(defn nav-bar []
  [:div [:p "navigation bar under construction."]])

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
               nav-bar
               components]
              [:script {:src "http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"}]
              [:script {:src "/bootstrap/js/bootstrap.min.js"}]]))
