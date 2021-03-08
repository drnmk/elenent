(ns elenent.layout.control
  (:require
   [hiccup.core :as c]
   [hiccup.page :as p]))

(def title "elenent investment manager")
(def project-links
  {:meta-content (str "width=device-width, "
                      "initial-scale=1.0")
   :style (str  "https://cdn.jsdelivr.net/npm/"
                "bulma@0.9.1/css/bulma.min.css")
   :icon (str  "https://maxst.icons8.com/vue-static/"
               "landings/line-awesome/line-awesome/"
               "1.3.0/css/line-awesome.min.css")})

(defn page-frame [& components]
  (p/html5
   {:lang :en}
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
