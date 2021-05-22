(ns elenent.page)

(defn index []
  [:div {:class :container}
   [:div {:class :container} "Elenent - Investment Accounting Solution"]
   [:a {:href "http://localhost:1234/signup"} "Sign Up"]
   [:a {:href "http://localhost:1234/signin"} "Sign In"]])

(defn sign-up []
  [:form {:action "aaa" :method "post"}
   [:div {:class :container}
    [:label {:for :username} "Username"]
    [:input {:type :text :placeholder "Enter username" :name "username" :required true}]
    [:label {:for :password} "Password"]
    [:input {:type :password :placeholder "Enter password" :name :password :required true}]
    [:button {:type :submit} "Login"]
    [:input {:type :checkbox :checked false :name :remember} "Remember me"]]])

(defn sign-in []
  [:form {:action "aaa" :method "post"}
   [:div {:class :container}
    [:label {:for :username} "Username"]
    [:input {:type :text :placeholder "Enter username" :name "username" :required true}]
    [:label {:for :password} "Password"]
    [:input {:type :password :placeholder "Enter password" :name :password :required true}]
    [:button {:type :submit} "Login"]
    [:input {:type :checkbox :checked false :name :remember} "Remember me"]]])


(defn deal-table []
  [:div {:class "container is-centered"}
   [:table
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
       [:td "10-Year Ultra Bond"]]]]]])


