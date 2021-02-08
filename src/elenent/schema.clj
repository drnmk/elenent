(ns elenent.schema
  (:require
   [java-time :as jtm]))

(def cardis
  #{:cardi/many
    :cardi/one})

(def unis
  #{:uni/universal
    :uni/cliental
    :uni/portfolio
    :uni/anywhere})

(def vtypes
  #{:vtype/text
    :vtype/int
    :vtype/float
    :vtype/double
    :vtype/date
    :vtype/time
    :vtype/stamp})

(def timings
  #{:opening
    :closing
    :average})

(def breeds
  #{:future})

(def traits
  {
   ;; client 
   :client/legal-name [:uni/universal :cardi/one :vtype/text]
   :client/short-name [:uni/universal :cardi/one :vtype/text]
   :client/joined-on [:uni/anywhere :cardi/one :vtype/date]
     
   ;; service
   :service/name [:uni/universal :cardi/one :vtype/text]
   :service/pricing [:uni/anywhere :cardi/one :vtype/float]
   
   ;; portfolio
   :portfolio/name [:uni/cliental :cardi/one :vtype/text]
   :portfolio/client-ent [:uni/anywhere :cardi/one :vtype/uuid]

   ;; rate
   :rate/ticker [:uni/anywhere :cardi/one :vtype/uuid]
   :rate/date [:uni/anywhere :cardi/one :vtype/uuid]
   :rate/timing [:uni/anywhere :cardi/one :vtype/uuid]
   :rate/number [:uni/anywhere :cardi/one :vtype/uuid]
      
   ;; security 
   :security/ticker [:uni/universal :cardi/one :vtype/text]
   :security/breed [:uni/anywhere :cardi/one :vtype/text]
   :security/started-on [:uni/universal :cardi/one :vtype/date]
   :security/matured-on [:uni/universal :cardi/one :vtype/text]
   :security/multiplier [:uni/anywhere :cardi/one :vtype/int]
   
   ;; position 
   :position/portfolio-ent [:uni/anywhere :cardi/one :vtype/uuid]
   :position/user-group-01 [:uni/anywhere :cardi/one :vtype/text]
   :position/user-group-02 [:uni/anywhere :cardi/one :vtype/text]
   :position/user-group-03 [:uni/anywhere :cardi/one :vtype/text]
   :position/user-group-04 [:uni/anywhere :cardi/one :vtype/text]
   :position/user-group-05 [:uni/anywhere :cardi/one :vtype/text]
   :position/user-group-06 [:uni/anywhere :cardi/one :vtype/text]
   :position/user-group-07 [:uni/anywhere :cardi/one :vtype/text]
   :position/user-group-08 [:uni/anywhere :cardi/one :vtype/text]
   :position/user-group-09 [:uni/anywhere :cardi/one :vtype/text]
   :position/user-group-10 [:uni/anywhere :cardi/one :vtype/text]
   :position/user-group-11 [:uni/anywhere :cardi/one :vtype/text]
   :position/user-group-12 [:uni/anywhere :cardi/one :vtype/text]
   :position/breed-id [:uni/anywhere :cardi/one :vtype/text]
   :position/traded-on [:uni/anywhere :cardi/one :vtype/date]
   :position/started-on [:uni/anywhere :cardi/one :vtype/date]
   :position/terminated-on [:uni/anywhere :cardi/one :vtype/date]
   :position/notional [:uni/anywhere :cardi/one :vtype/int]

   ;; transact 
   :transact/date [:uni/anywhere :cardi/one :vtype/date]
   :transact/activity [:uni/anywhere :cardi/one :vtype/text]
   :transact/position-ent [:uni/anywhere :cardi/one :vtype/uuid]
   
   ;; journal 
   :journal/transact-ent [:uni/anywhere :cardi/one :vtype/uuid]})

