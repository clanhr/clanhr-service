(ns leiningen.new.clanhr-service
  (:use [leiningen.new.templates :only [renderer name-to-path ->files year]]))

(def render (renderer "clanhr-service"))

(defn cap [s]
  (str (.toUpperCase (subs s 0 1)) (subs s 1)))

(defn clanhr-service
  "Skeleton Clojure project"
  [name]
  (let [data {:name name
              :upper-name (cap name)
              :upper-and-sanitized-name (.toUpperCase (name-to-path name))
              :lower-name (.toLowerCase name)
              :sanitized (name-to-path name)
              :year (year)}]
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["scripts/autotest" (render "autotest" data) :executable true]
             [".gitignore" (render ".gitignore" data)]
             ["src/{{sanitized}}/controllers/healthcheck.clj" (render "healthcheck.clj" data)]
             ["src/{{sanitized}}/controllers/routes.clj" (render "routes.clj" data)]
             ["src/{{sanitized}}/controllers/client.clj" (render "client.clj" data)]
             ["test/{{sanitized}}/controllers/healthcheck_test.clj" (render "healthcheck_test.clj" data)])))
