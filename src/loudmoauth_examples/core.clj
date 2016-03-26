(ns loudmoauth-examples.core
  (:require [loudmoauth.core :as lm]
            [ring.adapter.jetty :as ringj]
            [ring.util.response :as ringr]
            [ring.middleware.params :as ringp]
            [ring.middleware.keyword-params :as ringkp]))

(defn handler [request]
  (println request)
  (condp = (:uri request)
     "/oauth2" (lm/parse-code request)
      "/interact"  (ringr/redirect (lm/user-interaction))  
    {:status 200
     :body (:uri request)
     }))

(def spotify-oauth2-params
  {:base-url "https://accounts.spotify.com"
   :client-id (System/getenv "SPOTIFY_OAUTH2_CLIENT_ID")
   :redirect-uri "http://localhost:3000/oauth2"
   :scope "playlist-read-private user-follow-modify"
   :custom-query-params {:show-dialog "true"}
   :client-secret (System/getenv "SPOTIFY_OAUTH2_CLIENT_SECRET")
   :provider :spotify
   })

(defn start-server
  "Starts our test web server."
  []
 (future (ringj/run-jetty (ringp/wrap-params (ringkp/wrap-keyword-params handler))  {:port 3000})))
