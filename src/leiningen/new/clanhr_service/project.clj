(defproject {{name}} "1.0.0-SNAPSHOT"
  :description "{{upper-name}} service"

  :dependencies [[environ "1.0.0"]
                 [org.clojure/clojure "1.7.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-json "0.4.0"]
                 [ring-cors "0.1.7"]
                 [org.clojure/data.json "0.2.6"]
                 [com.novemberain/validateur "2.4.2"]
                 [compojure "1.4.0"]
                 [ragtime "0.5.1"]
                 [aleph "0.4.0"]
                 [postgresql "9.3-1102.jdbc41"]
                 [clanhr/result "0.9.2"]
                 [clanhr/reply "0.5.0"]
                 [clanhr/validators "0.5.2"]
                 [clanhr/auth "0.4.0"]
                 [clanhr/analytics "1.4.0"]
                 [clanhr/ring-test-client "0.1.0"]
                 [clanhr/postgres-gateway "0.9.3" :exclusions [[io.netty/netty-handler]]]
                 [clanhr/memory-gateway "0.8.0"]]

  :plugins [[lein-ring "0.8.10"]
            [lein-environ "1.0.0"]
            [lein-ancient "0.6.8-SNAPSHOT"]]

  :aliases {"migrate"  ["run" "-m" "clanhr.{{sanitized}}.config.database/migrate"]
                        "rollback" ["run" "-m" "clanhr.{{sanitized}}.config.database/rollback"]}
  
  :source-paths ["src"]
  :test-paths ["test"]

  :main clanhr.{{sanitized}}.controllers.routes
  :ring {:handler clanhr.{{sanitized}}.controllers.routes/app}
  :uberjar-name "clanhr.{{sanitized}}.jar"

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
                       :dependencies [[org.clojure/test.check "0.8.0"]
                                      [ring-mock "0.1.5"]
                                      [criterium "0.4.3"]]
                       :global-vars {*warn-on-reflection* false
                                     *assert* true}
                       :plugins [[com.jakemccrary/lein-test-refresh "0.9.0"]
                                 [lein-cloverage "1.0.2"]]}}

  :test-refresh {:quiet true})
