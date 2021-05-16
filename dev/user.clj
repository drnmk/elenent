(ns user
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.namespace.repl :refer (refresh)]
            [elenent.system :as app]))

(def configs
  {:server {:options {:port 1234
                      :join? false}}
   :db {:options {:port 0
                  :counter-init 0}}})

(def system nil)

(defn init []
  (alter-var-root
   #'system
   (constantly (app/make-system configs))))

(defn start []
  (alter-var-root
   #'system
   component/start))

(defn stop []
  (alter-var-root
   #'system
   (fn [s] (when s (component/stop s)))))

(defn go []
  (init)
  (start))

(defn reset []
  (stop)
  (refresh :after 'user/go))
