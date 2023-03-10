(defproject apple "0.1.0-SNAPSHOT"
  :description "Apple Rest api"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :min-lein-version "2.0.0"
  :repl-options { :init-ns apple.user
                  :init (require '[clojure.repl :refer :all])}
  :dependencies [[ring "1.9.6"]
                 [integrant "0.8.0"]
                 [environ "1.2.0"]
                 [clj-http "3.12.3"]
                 [migratus "1.4.9"]
                 [cheshire "5.11.0"]
                 [com.github.seancorfield/next.jdbc "1.3.847"]
                 [seancorfield/next.jdbc "1.2.659"]
                 [metosin/reitit "0.5.18"]
                 [metosin/muuntaja "0.6.8"]
                 [ovotech/ring-jwt "2.3.0"]
                 [org.clojure/clojure "1.10.0"]
                 [org.postgresql/postgresql "42.5.2"]]
  :main ^:skip-aot apple.server
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:source-paths ["dev/src"]
                   :resource-paths ["dev/resources"]
                   :dependencies [[ring/ring-mock "0.4.0"]
                                  [integrant/repl "0.3.2"]
                                  [environ "1.2.0"]
                                  [metosin/reitit "0.5.18"]
                                  [seancorfield/next.jdbc "1.2.659"]]}}
  :uberjar-name "apple.jar")
