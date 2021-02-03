(ns elenent.ui
  (:require
   [hiccup.page :as hcp :refer [html5]]
   [hiccup.core :as hcc :refer [html h]]))

(def layout-links
  {:meta-content "width=device-width, initial-scale=1.0"
   :style "/bootstrap/css/bootstrap.min.css"
   :ajax-js "http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"
   :bootstrap-js "/bootstrap/js/bootstrap.min.js"})

(defn layout [& components]
  (hcp/html5 {:lang :en}
             [:head
              [:title "Elenent!"]
              [:meta {:name :viewport
                      :content (layout-links :meta-content)}]
              [:link {:href (layout-links :style)
                      :rel :stylesheet}]]
             [:body
              [:div components]
              [:script {:src (layout-links :ajax-js) }]
              [:script {:src (layout-links :bootstrap-js)}]]))

(defn navbar []
  [:div.container
   [:h1 "hello navbar."]])

(defn sign-up-client-comp []
  [:div.container
   [:h1 "Sign Up as Investor Group"]
   [:div.form-group 
    [:label {:for "usr"} "Investor Group Name:"]
    [:input {:type "text"
             :class "form-control"
             :id "usr"}]]
   [:div.form-group 
    [:label {:for "usr"} "Investor Group Email:"]
    [:input {:type "text"
             :class "form-control"
             :id "usr"}]]
   [:div.form-group 
    [:label {:for "usr"} "Investor Group Password:"]
    [:input {:type "text"
             :class "form-control"
             :id "usr"}]]
   [:input {:type "submit"
            :class "btn btn-info"
            :value "Submit Button"}]
   ])


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
