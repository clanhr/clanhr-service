(ns clanhr.{{name}}.controllers.routes
  (:gen-class)
  (:require [clojure.stacktrace]
            [compojure.handler :as handler]
            [clanhr.auth.auth-middleware :as auth]
            [clanhr.logger.middleware :as logger-middleware]
            [clanhr.analytics.errors :as errors]
            [clanhr.analytics.metrics :as metrics]
            [clanhr.{{name}}.controllers.healthcheck :as healthcheck]
            [aleph.http :as http]
            [clanhr.reply.core :as reply]
            [clojure.core.async :as a]
            [ring.middleware.params :as ring-params]
            [ring.middleware.cors :as cors]
            [compojure.core :as core :refer [GET POST PUT DELETE defroutes]]
            [compojure.route :as route]))

(defroutes public-routes
  (GET "/healthcheck" [] (healthcheck/handler)))

(defroutes private-routes
  (route/not-found (reply/not-found {:success false :data "not-found"})))

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
            (logger-middleware/run :{{sanitized}})
            (auth/run)))
      (compojure.handler/api)
      (wrap-exception-handler)
      (setup-cors)
      (metrics/http-request-metric-fn "{{name}}")))

(defn- get-port
  "Gets the port to run the service"
  []
  (or (get (System/getenv) "PORT")
      (get (System/getenv) "CLANHR_{{upper-and-sanitized-name}}_PORT")
      "5000"))

(defn -main [& args]
  (let [port (Integer/parseInt (get-port))]
    (println "**" (or (get (System/getenv) "{{upper-and-sanitized-name}}_ENV" "development")))
    (println "Running on port" port)
    (http/start-server app {:port port})))
