(ns loudmoauth-examples.core
  (:require [loudmoauth.core :as lm]
            [ring.adapter.jetty :as ringj]
            [ring.util.response :as ringr]
            [ring.middleware.params :as ringp]
            [ring.middleware.keyword-params :as ringkp]))

(defn handler [request]
  (condp = (:uri request)
     "/oauth2" (lm/parse-params request)
      "/interact"  (ringr/redirect (lm/user-interaction))  
    {:status 200
     :body (:uri request)}))

(def spotify-oauth2-params
  {:base-url "https://accounts.spotify.com"
   :auth-endpoint "/authorize"
   :token-endpoint "/api/token"
   :client-id (System/getenv "SPOTIFY_OAUTH2_CLIENT_ID")
   :redirect-uri "http://localhost:3000/oauth2"
   :scope "playlist-read-private user-follow-modify"
   :custom-query-params {:show-dialog "true"}
   :client-secret (System/getenv "SPOTIFY_OAUTH2_CLIENT_SECRET")
   :provider :spotify})

(def soundcloud-oauth2-params 
  {:base-url "https://api.soundcloud.com"
   :auth-endpoint "/connect"
   :token-endpoint "/oauth2/token"
   :client-id (System/getenv "SOUNDCLOUD_OAUTH2_CLIENT_ID")
   :redirect-uri "http://localhost:3000/oauth2"
   :scope "non-expiring"
   :custom-query-params {:display "popup"}
   :client-secret (System/getenv "SOUNDCLOUD_OAUTH2_CLIENT_SECRET")
   :provider :soundcloud})

(def linkedin-oauth2-params 
  {:base-url "https://www.linkedin.com/oauth/v2"
   :auth-endpoint "/authorization"
   :token-endpoint "/accessToken"
   :client-id (System/getenv "LINKEDIN_OAUTH2_CLIENT_ID")
   :redirect-uri "http://localhost:3000/oauth2"
   :scope "r_basicprofile"
   :client-secret (System/getenv "LINKEDIN_OAUTH2_CLIENT_SECRET")
   :provider :linkedin})


(defn start-server
  "Starts our test web server."
  []
 (future (ringj/run-jetty (ringp/wrap-params (ringkp/wrap-keyword-params handler))  {:port 3000})))
