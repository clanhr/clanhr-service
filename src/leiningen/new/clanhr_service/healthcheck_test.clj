(ns clanhr.{{sanitized}}.controllers.healthcheck-test
  (use clojure.test)
  (:require [result.core :as result]
            [clanhr.{{sanitized}}.controllers.client :as client]
            [clanhr.{{sanitized}}.controllers.healthcheck :as healthcheck]))

(deftest basic-test
  (testing "Ping returns a pong"
    (let [response (client/http-get "/ping")]
      (is "pong" (:body response))))
  (testing "Healthcheck returns OK"
    (let [response (client/http-get "/healthcheck")]
      (is (= 200 (:status response)))
      (is (= "{{name}}" (get-in response [:body :name]))))))
