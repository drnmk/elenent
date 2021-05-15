(ns elenent.schema
  ;;(:require [clojure.spec.alpha :as s])
  )

(comment 

  (def abilities
    {:admin "can do all"
     :approver "can view, prepare, and approve"
     :preparer "can view and prepare"
     :viewer "can view"})

  (def breeds
    #{:future
      :option
      :swap
      :forward})

  (s/def :client/legal-name string?)
  (s/def :client/short-name string?)
  (s/def :client/email string?)
  (s/def :client/address string?)
  (s/def :client/phone string?)
  (s/def :client/base-currency currencies)
  (s/def :client/functional-currency currencies)
  (s/def :client/reporting-currency currencies)
  (s/def :client/description string?)

  (s/def :client/entity
    (s/keys :req [:crux.db/id
                  :client/legal-name
                  :client/short-name
                  :client/email
                  :client/address
                  :client/phone
                  :client/base-currency
                  :client/functional-currency
                  :client/reporting-currency
                  :client/description]))

  (s/def :contract/name string?)
  (s/def :contract/underlying underlyings)
  (s/def :contract/notional int?) 
  (s/def :contract/mature-on (partial instance? java.time.LocalDate))
  (s/def :contract/breed breeds)



  (def counterparties
    {:client "the client"
     :BOA "Bank of America"
     :GS "Goldman Sachs"
     :WLF "Wells Fargo"
     :RBC "Royal Bank of Canada"
     :RBS "Royal Bank of Scotland"})


  (s/def :crux.db/id uuid?)


  (def currencies
    #{:ALL :DZD :ARS :AUD :BSD :BHD :BDT :AMD :BBD :BMD :BTN
      :BOB :BWP :BZD :SBD :BND :MMK :BIF :KHR :CAD :CVE :KYD
      :LKR :CLP :CNY :COP :KMF :CRC :HRK :CUP :CZK :DKK :DOP
      :SVC :ETB :ERN :FKP :FJD :DJF :GMD :GIP :GTQ :GNF :GYD
      :HTG :HNL :HKD :HUF :ISK :INR :IDR :IRR :IQD :ILS :JMD
      :JPY :KZT :JOD :KES :KPW :KRW :KWD :KGS :LAK :LBP :LSL
      :LRD :LYD :MOP :MWK :MYR :MVR :MUR :MXN :MNT :MDL :MAD
      :OMR :NAD :NPR :ANG :AWG :VUV :NZD :NIO :NGN :NOK :PKR
      :PAB :PGK :PYG :PEN :PHP :QAR :RUB :RWF :SHP :SAR :SCR
      :SLL :SGD :VND :SOS :ZAR :SSP :SZL :SEK :CHF :SYP :THB
      :TOP :TTD :AED :TND :UGX :MKD :EGP :GBP :TZS :USD :UYU
      :UZS :WST :YER :TWD :UYW :VES :MRU :STN :CUC :ZWL :BYN
      :TMT :GHS :SDG :UYI :RSD :MZN :AZN :RON :CHE :CHW :TRY
      :XAF :XCD :XOF :XPF :XBA :XBB :XBC :XBD :XAU :XDR :XAG
      :XPT :XTS :XPD :XUA :ZMW :SRD :MGA :COU :AFN :TJS :AOA
      :BGN :CDF :BAM :EUR :MXV :UAH :GEL :BOV :PLN :BRL :CLF
      :XSU :USN :XXX :NON})




  (def events
    #{:contract
      :effect
      :interest
      :reduce
      :terminate
      :mature})  




  
  (s/def :grouping/code string?)
  (s/def :grouping/description string?)

  (s/def :grouping/entity
    (s/keys :req [:crux.db/id
                  :grouping/code
                  :grouping/description]))




  (s/def :position/name string?)
  (s/def :position/stance stances)
  (s/def :position/quantity int?)

  (s/def :position/entity
    (s/keys :req [:crux.db/id
                  :position/name
                  :position/stance
                  :position/quantity]))






  (def spans
    "values are numbers of months"
    {:continuous 0
     :month 1
     :quarter 3
     :semiannual 6
     :annual 12
     :biannual 24})



  (def stances #{:pay
                 :receive
                 :buy
                 :sell})





  (def underlyings
    {:COMP "NASDAQ Composite Index"
     :DJIA "Dow Jones Industrial Average"
     :DJT "Dow Jones Transportation Average"
     :DJU "Dow Jones Utility Average Index"
     :GDOW "Global Dow Realtime"
     :IXCO "NASDAQ Computer Index"
     :MID "S&P 400 Mid Cap Index"
     :NDX "NASDAQ 100 Index (NASDAQ Calculation)"
     :NYA "NYSE Composite Index"
     :OEX "S&P 100 Index"
     :PSE "NYSE Arca Technology 100 Index"
     :RUI "Russell 1000 Index"
     :RUT "Russell 2000 Index"
     :SOX "PHLX Semiconductor Index"
     :SPX "S&P 500 Index"
     :XX-TNX "CBOE 10 Year Treasury Note Yield Index"
     :XX-TYX "CBOE 30 Year Treasury Bond Yield Index"
     :XAU "PHLX Gold/Silver Index"
     :XAX "NYSE American Composite Index"
     :XMI "NYSE Arca Major Market Index"
     :XX-XOI "NYSE Arca Oil Index"
     :FF "Fed Funds"
     :ED "Eurodollas"
     :TU "2-year Treasury Note"
     :FV "5-year Treasury Note"
     :TY "10-year Treasury Note"
     :US "30-year Treasury Note"})
  )
