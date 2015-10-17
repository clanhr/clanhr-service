(ns clanhr.{{name}}.controllers.client
  (:require [ring.util.codec :as codec]
            [clanhr.reply.json :as json]
            [clanhr.ring-test-client.core :as client]
            [clanhr.{{name}}.controllers.routes :as routes])
      (:use clojure.test
            ring.mock.request))

(def http-get (partial client/http-get routes/app))
(def auth-get (partial client/auth-get routes/app))
(def post (partial client/post routes/app))
(def auth-post (partial client/auth-post routes/app))
(def auth-put (partial client/auth-put routes/app))
