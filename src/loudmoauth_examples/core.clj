(ns loudmoauth-examples.core
  (:require [loudmoauth.core :as lm]
            [ring.adapter.jetty :as ringj]))

(defn handler [request]
  (condp = (:uri request)
     "/oauth2" (lm/parse-code request) 
    {:status 200
     :body (:uri request)
     }))

(def spotify-oauth2-params
  {:base-url "https://www.example.com"
   :client-id (System/getenv "SPOTIFY_OAUTH2_CLIENT_ID")
   :redirect-uri "https://www.example.com/callback"
   :scope "do-one-thing do-another-thing"
   :custom-query-params {:likes-cake "Yes"}
   :client-secret (System/getenv "SPOTIFY_OAUTH2_CLIENT_SECRET")})

