(ns loudmoauth-examples.core
  (:require [loudmoauth.core :as lm]
            [ring.adapter.jetty :as ringj]))

(defn handler [request]
  (condp (:uri request)
     "/oauth2" (lm/parse-code request) 
    {:status 200
     :body (:uri request)
     }))



