(ns clanhr.{{sanitized}}.controllers.routes
  (:use compojure.core)
  (:gen-class)
  (:require [clojure.stacktrace]
            [compojure.handler :as handler]
            [clanhr.auth.auth-middleware :as auth]
            [clanhr.analytics.errors :as errors]
            [clanhr.analytics.metrics :as metrics]
            [clanhr.{{sanitized}}.controllers.healthcheck :as healthcheck]
            [clanhr.reply.core :as reply]
            [clojure.data.json :as json]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.params :as ring-params]
            [ring.middleware.cors :as cors]
            [compojure.core :as core]
            [compojure.route :as route]))

(defroutes public-routes
  (GET "/healthcheck" [] (healthcheck/handler))
  (GET "/ping" [] "pong"))

(defroutes private-routes
  )

(defn- wrap-exception-handler
  [handler]
  (fn [req]
    (try
      (handler req)
      (catch Exception e
        (errors/request-exception e req)
        (reply/exception e)))))

(defn- setup-cors
  "Setup cors"
  [handler]
  (cors/wrap-cors handler
                  :access-control-allow-origin
                  [#"^https?://(.+\.)?clanhr.com(.*)"
                   #"^https?://clanhr(.+\.)?cloudapp.net(.*)"
                   #"^http://localhost(.*)"]
                  :access-control-allow-methods [:get :put :post :delete]))
(def app
  (-> (core/routes
        public-routes
        (-> (handler/api private-routes)
            (auth/run)))
      (compojure.handler/api)
      (wrap-exception-handler)
      (setup-cors)
      (metrics/http-request-metric-fn "{{name}}")))

(defn- get-port
  "Gets the port to run the service"
  []
  (or (get (System/getenv) "PORT")
      "5000"))

(defn -main [& args]
  (let [port (Integer/parseInt (get-port))]
    (println "**" (or (get (System/getenv) "{{upper-and-sanitized-name}}_ENV" "development")))
    (println "Running on port" port)
    (jetty/run-jetty app {:port port})))
