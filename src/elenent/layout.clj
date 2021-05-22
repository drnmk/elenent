(ns elenent.layout
  (:require [hiccup.core :refer [html]]))

(def title "elenent investment manager")

(def links
  {:meta-content (str "width=device-width, "
                      "initial-scale=1.0")
   :style (str  "https://cdn.jsdelivr.net/npm/"
                "bulma@0.9.1/css/bulma.min.css")
   :icon (str  "https://maxst.icons8.com/vue-static/"
               "landings/line-awesome/line-awesome/"
               "1.3.0/css/line-awesome.min.css")})

(defn frame [elem]
  (html
   {:lang :en}
   [:head
    [:title title]
    [:meta {:name :viewport
            :content (:meta-content links)}]
    [:link {:rel :stylesheet
            :href (:style links)}]
    [:link {:rel :stylesheet
            :href (:icon links)}]]
   [:body
    [:div elem]]))
