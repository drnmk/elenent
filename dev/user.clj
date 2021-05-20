(ns user
  (:require [com.stuartsierra.component :refer [start stop]]
            [clojure.tools.namespace.repl :refer [refresh]]
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

(defn begin []
  (alter-var-root
   #'system
   start))

(defn end []
  (alter-var-root
   #'system
   (fn [s] (when s (stop s)))))

(defn go []
  (init)
  (begin))

(defn reset []
  (end)
  (refresh :after 'user/go))
