(ns clanhr.waza.controllers.healthcheck
  (:require [clanhr.reply.core :as reply]))

(defn handler
  "Healthcheck"
  []
  (reply/ok {:name "waza"}))
