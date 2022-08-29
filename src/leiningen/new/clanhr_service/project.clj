(defproject {{name}} "1.0.0-SNAPSHOT"
  :description "{{upper-name}} service"

  :license {:name         "The MIT License"
            :url          "file://LICENSE"
            :distribution :repo
            :comments     "Copyright Selfcare All Rights Reserved."}

  :min-lein-version "2.5.0"

  :dependencies [[environ "1.2.0"]
                 [org.clojure/clojure "1.7.0"]
                 [mvxcvi/puget "1.3.2"]
                 [clj-http "3.12.3"]
                 [ring/ring-core "1.9.5"]
                 [ring/ring-json "0.5.1"]
                 [ring-cors "0.1.13"]
                 [org.clojure/data.json "2.4.0"]
                 [com.novemberain/validateur "2.6.0"]
                 [compojure "1.7.0"]
                 [ragtime "0.8.1"]
                 [aleph "0.5.0"]
                 [postgresql "9.3-1102.jdbc41"]
                 [clanhr/result "0.16.0"]
                 [clanhr/reply "1.1.1"]
                 [clanhr/logger "0.5.0"]
                 [clanhr/validators "0.6.0"]
                 [clanhr/auth "1.32.0"]
                 [clanhr/analytics "2.0.0"]
                 [clanhr/ring-test-client "0.4.0"]
                 [clanhr/postgres-gateway "1.12.1" :exclusions [[io.netty/netty-handler]]]
                 [clanhr/memory-gateway "0.9.0"]]

  :plugins [[lein-ring "0.8.10"]
            [lein-environ "1.0.0"]
            [lein-ancient "0.6.8-SNAPSHOT"]]

  :aliases {"migrate"  ["run" "-m" "clanhr.{{sanitized}}.config.database/migrate"]
                        "rollback" ["run" "-m" "clanhr.{{sanitized}}.config.database/rollback"]}

  :source-paths ["src"]
  :test-paths ["test"]

  :main clanhr.{{sanitized}}.controllers.routes
  :ring {:handler clanhr.{{sanitized}}.controllers.routes/app}
  :uberjar-name "clanhr.{{name}}.jar"

  :profiles {:uberjar {:aot :all
                       :env {:gateway-provider :postgres}}
                       :production {:env {:production true
                                          :gateway-provider :postgres}}
                       :test {:env {:test true
                                    :clanhr-env "test"
                                    :secret "test"
                                    :allow-drop-db "true"
                                    :gateway-provider :memory}}
                       :dev {:env {:dev true
                                   :secret "test"
                                   :gateway-provider :postgres}
                       :dependencies [[org.clojure/test.check "1.1.1"]
                                      [ring-mock "0.1.5"]
                                      [criterium "0.4.6"]]
                       :global-vars {*warn-on-reflection* false
                                     *assert* true}
                       :plugins [[com.jakemccrary/lein-test-refresh "0.9.0"]
                                 [lein-cloverage "1.0.2"]]}}

  :test-refresh {:quiet true})
