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
             ["script/autotest" (render "autotest" data) :executable true]
             ["script/autoupgrade" (render "autoupgrade" data) :executable true]
             ["script/run" (render "run" data) :executable true]
             [".gitignore" (render ".gitignore" data)]
             ["src/clanhr/{{sanitized}}/controllers/healthcheck.clj" (render "healthcheck.clj" data)]
             ["src/clanhr/{{sanitized}}/controllers/routes.clj" (render "routes.clj" data)]
             ["src/clanhr/{{sanitized}}/controllers/client.clj" (render "client.clj" data)]
             ["test/clanhr/{{sanitized}}/controllers/healthcheck_test.clj" (render "healthcheck_test.clj" data)])))
