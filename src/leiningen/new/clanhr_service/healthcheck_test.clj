(ns clanhr.{{name}}.controllers.healthcheck-test
  (use clojure.test)
  (:require [result.core :as result]
            [clanhr.{{name}}.controllers.client :as client]
            [clanhr.{{name}}.controllers.healthcheck :as healthcheck]))

(deftest basic-test
  (testing "Healthcheck returns OK"
    (let [response (client/http-get "/healthcheck")]
      (is (= 200 (:status response)))
      (is (= "{{name}}" (get-in response [:body :name]))))))
