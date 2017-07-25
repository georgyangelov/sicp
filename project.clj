(defproject sicp "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot sicp
  :target-path "target/%s"
  :test-paths ["."]
  :profiles {:uberjar {:aot :all}})
