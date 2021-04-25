(ns elenent.comps.contract.view
  (:require
   [hiccup.core :as c]
   [hiccup.page :as p]
   [elenent.model.contract :refer :all]))

(defn view-futures-table [futures]
  [:div 
   [:p "Contracts"]
   [:div 
    [:table 
     [:thead  
      [:tr
       [:th "Serial"]
       [:th "Category"]
       [:th "Cusip"]
       [:th "Multiplier"]
       [:th "Mature On"]]]
     [:tbody
      (for [future futures]
        [:tr  
         [:td (nth future 0)]
         [:td (nth future 1)]
         [:td (nth future 2)]
         [:td (nth future 3)]
         [:td (nth future 4)]])]]]])


(defn view-new-form []
  )
