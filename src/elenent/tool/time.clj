(ns elenent.tool.time
  (:refer-clojure
   :exclude [range iterate format max min])
  (:require
   [java-time :as jt]))

(defn make-date-zoned [{:keys [year month day]}]
  (jt/zoned-date-time year month day))

(defn make-years [n]
  (jt/years n))

(defn make-months [n]
  (jt/months n))

(defn make-days [n]
  (jt/days n))

(defn make-date [{:keys [year month day]}]
  (jt/local-date year month day))

(defn sqldate->localdate [sqldate]
  (jt/local-date sqldate))

(defn make-stamp []
  (jt/instant->sql-timestamp 
   (jt/instant)))

(defn str->date [s]
  (jt/local-date "yyyy-MM-dd" s))

(defn date->str [d]
  (jt/format "yyyy-MM-dd" d))

(defn ensure-date-type [x]
  (if (string? x)
    (str->date x)
    x))

(defn ensure-date-string [x]
  (if (string? x)
    x (date->str x)
    ))

(defn add [{:keys [date span]}]
  (jt/plus date span))

(defn subtract [{:keys [date span]}]
  (jt/minus date span))

(defn until [{:keys [earlier later]}]
  (let [ens-earlier (ensure-date-type earlier)
        ens-later (ensure-date-type later)]
    (.until ens-earlier ens-later (java.time.temporal.ChronoUnit/DAYS))))

(defn later? [earlier later]
  (> (until {:earlier earlier :later later}) 0))
