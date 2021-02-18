(ns elenent.view
  (:require
   [hiccup.page :refer [html5]]
   [hiccup.core :refer [html h]]
   [garden.core :refer [css]]))

(def title
  "elenent investment manager")

(def project-links
  {:meta-content "width=device-width, initial-scale=1.0"
   :style "https://cdn.jsdelivr.net/npm/bulma@0.9.1/css/bulma.min.css"
   :icon "https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css"})

(defn layout [& components]
  (html5 {:lang :en}
             [:head
              [:title title]
              [:meta {:name :viewport
                      :content (:meta-content project-links)}]
              [:link {:rel :stylesheet
                      :href (:style project-links)}]
              [:link {:rel :stylesheet
                      :href (:icon project-links)}]]
             [:body
              [:div
               components]]))

(defn positions [positions]
  [:div
   [:h1 "Positions"]
   [:table {:class "table"}
    [:thead  
     [:tr
      [:th "Serial"]
      [:th "Position Name"]
      [:th "Kind"]
      [:th "Description"]]]
    [:tbody
     (for [position positions]
       [:tr  
        [:td (nth position 0)]
        [:td (nth position 1)]
        [:td (nth position 2)]
        [:td (nth position 3)]])]]])

(defn position-form []
  [:form {:method "POST" :action "/positions"}
   [:h1 "Enter new position"]
   ;;
   [:div {:class "field"}
    [:label {:class "label"} "Position Name"]
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




