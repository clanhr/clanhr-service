(ns clanhr.waza.controllers.healthcheck-test
  (use clojure.test)
  (:require [result.core :as result]
            [clanhr.waza.controllers.client :as client]
            [clanhr.waza.controllers.healthcheck :as healthcheck]))

(deftest basic-test
  (testing "Healthcheck returns OK"
    (let [response (client/http-get "/healthcheck")]
      (is (= 200 (:status response)))
      (is (= "waza" (get-in response [:body :name]))))))
