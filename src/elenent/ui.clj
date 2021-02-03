(ns elenent.ui
  (:require
   [hiccup.page :refer [html5]]
   [hiccup.core :refer [html h]]))

(def layout-links
  {:meta-content "width=device-width, initial-scale=1.0"
   :style "/bootstrap/css/bootstrap.min.css"
   :ajax-js "http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"
   :bootstrap-js "/bootstrap/js/bootstrap.min.js"})

(defn layout [& components]
  (html5 {:lang :en}
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

(defn greet-page []
  [:div.container
   [:h1 "hello, this is elenent app in contstruction."]
   [:a {:href "/client-sign-up"} "Go to client-sign-up page!"]])

(defn sign-up-client-comp []
  [:form.form-horizontal
    {:method "POST" :action "/clients"}
 
   [:div.form-group 
     [:label.control-label.col-sm-2 {:for :name-input} "Name"]
     [:div.col-sm-10
      [:input#name-input.form-control {:name :name :placeholder "Name"}]]]

   [:div.form-group 
    [:label.control-label.col-sm-2 {:for :email-input} "Email"]
    [:div.col-sm-10
     [:input#email-input.form-control {:name :email :placeholder "Email"}]]]
   
   [:div.form-group
     [:div.col-sm-offset-2.col-sm-10
      [:input.btn.btn-primary {:type :submit :value "Sign Up"}]]]])





