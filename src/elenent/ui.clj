(ns elenent.ui
  (:require
   [hiccup.page :refer [html5]]
   [hiccup.core :refer [html h]]
   [garden.core :refer [css]]))

(css [:body {:font-size "16px"}])

(def layout-links
  {:meta-content "width=device-width, initial-scale=1.0"
   :style "/css/style.css"
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
              [:div components]]))

(defn greet-page []
  [:main
   
   [:section {:class "glass"}

    [:div {:class "dashboard"}

     [:div {:class "user"}
      [:img {:src "./image/sackboy.png" :alt ""}]
      [:h2 "ABC, LLC"]
      [:p "Elenent Pro Member"]]

     [:div {:class "links"}

      [:div {:class "link"}
       [:img {:src "./image/twitch.png" :alt ""}]
       [:h3 "position"]]

      [:div {:class "link"}
       [:img {:src "./image/steam.png" :alt ""}]
       [:h3 "projection"]]

      [:div {:class "link"}
       [:img {:src "./image/upcoming.png" :alt ""}]
       [:h3 "transaction"]]

      [:div {:class "link"}
       [:img {:src "./image/library.png" :alt ""}]
       [:h3 "accounting"]]]

     [:div {:class "pro"}
      [:h2 "Tech Support"]
      ;; [:img {:src "./image/controller.png" :alt ""}]
      ]
     ]

    [:div {:class "games"}
     [:div {:class "status"}
      [:h1 "Portfolio A"]
      [:input {:type "text"}]]
     [:div {:class "cards"}

      [:div {:class "card"}
       [:img {:src "./image/assassins.png" :alt ""}]
       [:div {:class "card-info"}
        [:h2 "SPX12BIOP"]
        [:p "EMINI Future"]
        [:div {:class "progress"}]]
       [:h2 {:class "percentage"} "60%"]]

      [:div {:class "card"}
       [:img {:src "./image/sackboy.png" :alt ""}]
       [:div {:class "card-info"}
        [:h2 "123TSLA"]
        [:p "Call Option"]
        [:div {:class "progress"}]]
       [:h2 {:class "percentage"} "60%"]]
      
      [:div {:class "card"}
       [:img {:src "./image/spiderman.png" :alt ""}]
       [:div {:class "card-info"}
        [:h2 "ZX123FDSA"]
        [:p "Treasury Future"]
        [:div {:class "progress"}]]
       [:h2 {:class "percentage"} "60%"]]]]]])

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





