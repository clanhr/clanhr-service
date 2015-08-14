(ns clanhr.{{sanitized}}.controllers.healthcheck
  (:require [clanhr.reply.core :as reply]))

(defn handler
  "Healthcheck"
  []
  (reply/ok {:name "{{name}}"}))
