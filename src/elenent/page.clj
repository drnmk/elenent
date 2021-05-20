(ns elenent.page
  (:require [hiccup.core :refer [html]]
            [elenent.style :as elstyle]))

(defn deal-table []
  [:table {:class (elstyle/table)}
   [:thead
    [:tr
     [:th "tag"]
     [:th "name"]
     [:th "underlying"]]]
   [:thead
    [:tbody
     [:tr
      [:td "Future"]
      [:td "FDG"]
      [:td "2-Year Treasury Bond"]]
     [:tr
      [:td "Future"]
      [:td "ZCF"]
      [:td "10-Year Ultra Bond"]]]]])
