(ns elenent.model.crux
  (:require
   [clojure.spec.alpha :as s]))

(s/def :crux.db/id uuid?)
