(ns loudmoauth-examples.core
  (:require [loudmoauth.core :as lm]
            [ring.adapter.jetty :as ringj]
            [ring.util.response :as ringr]
            ))

(defn handler [request]
  (println request)
  (condp = (:uri request)
     "/oauth2" (lm/parse-code request)
      "/interact"  (ringr/redirect (lm/user-interaction request))  
    {:status 200
     :body (:uri request)
     }))

(def spotify-oauth2-params
  {:base-url "https://accounts.spotify.com"
   :client-id (System/getenv "SPOTIFY_OAUTH2_CLIENT_ID")
   :redirect-uri "http://localhost:3000/oauth2"
   :scope "playlist-read-private user-follow-modify"
   :custom-query-params {:show-dialog "true"}
   :client-secret (System/getenv "SPOTIFY_OAUTH2_CLIENT_SECRET")})

