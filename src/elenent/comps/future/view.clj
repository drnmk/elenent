(ns elenent.comps.future.view
  (:require
   [hiccup.core :as c]
   [hiccup.page :as p]
   [elenent.comps.future.schema :refer :all]))

  (defn view-futures-table [futures]
    [:div
     [:h1 "Futures"]
     [:table {:class "table"}
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
          [:td (nth future 4)]])]]])

  (defn view-future-form []
    [:form {:method "POST" :action "/futures"}
     [:h1 "Enter new future"]
     ;;
     [:div {:class "field"}
      [:label {:class "label"} "Future Name"]
      [:div {:class "control has-icons-left has-icons-right"}
       [:input {:class "input is-success"
                :type "text"
                :name :name
                :placeholder "enter here"}]
       [:span {:class "icon is-small is-left"}
        [:i {:class "fas fa-user"}]]
       [:span {:class "icon is-small is-right"}
        [:i {:class "fas fa-check"}]]]]
     ;; 
     [:div {:class "field"}
      [:label {:class "label"} "Subject"]
      [:div {:class "control"}
       [:div {:class "select"}
        [:select  
         [:option "Future"]
         [:option "Option"]]]]]
     ;;
     [:div {:class "field"}
      [:label {:class "label"} "Message"]
      [:div {:class "control"}
       [:textarea {:class "textarea"
                   :name :desc
                   :placeholder "Textarea"}]]]
     ;;
     [:div {:class "field is-grouped"}
      [:div {:class "control"}
       [:button {:class "button is-link"} "Submit"]]
      [:div {:class "control"}
       [:button {:class "button is-link is-light"} "Cancel"]]]])

(defn view-new-form []
  [:div {:class "hero is-primary is-fullheight"}
   [:div {:class "hero-body"}
    [:div {:class "container"}
     [:div {:class "columns is-centered"}
      [:div {:class "column is-4"}
       [:form {:method "POST"
               :action "/futures"
               :class "box"}

        [:div {:class "field"}
         [:label {:class "label"}
          "Underlying"]
         [:div ;;  {:class "dropdown is-active"}
          [:div {:class "dropdown-trigger"}
           [:button {:class "button"
                   ;;  :aria-haspopup "true"
                     :aria-controls "dropdown-menu"}
            [:span "Select"]
           ;;  [:span {:class "icon is-small"} [:i {:class "fas fa-angle-down" :aria-hidden "true"}]]

            ]]
          [:div {:class "dropdown-menu"
                 :id "dropdown-menu"
                 :role "menu"}
           [:div {:class "dropdown-content"}
            (for [category underlying-category]
              [:a {:class "dropdown-item"}
               (name category)])]]]]
           
        [:div {:class "field"}
         [:label {:class "label"} "Cusip"]
         [:div {:class "control has-icons-left"}
          [:input {:type "text"
                   :class "input"
                   :placeholder "Type Cusip in."}]
          [:span {:class "icon is-small is-left"}
           [:i {:class "fa fa-envelope"}]]]]

        [:div {:class "field"}
         [:label {:class "label"} "Multiplier"]
         [:div {:class "control has-icons-left"}
          [:input {:type "number"
                   :class "input"
                   :placeholder "Tpe Multiplier in Integer."}]
          [:span {:class "icon is-small is-left"}
           [:i {:class "fa fa-envelope"}]]]]

        [:div {:class "field"}
         [:label {:class "label"} "Mature On"]
         [:div {:class "control has-icons-left"}
          [:input {:type "date"
                   :class "input"
                   :placeholder "john@example.com"}]
          [:span {:class "icon is-small is-left"}
           [:i {:class "fa fa-envelope"}]]]]

        [:div {:class "field"}
         [:button {:class "button is-success"} "Login"]]]]]]]])
