(ns apple.server
  (:require [apple.config :as a] 
            [ring.adapter.jetty :as jetty]
            [integrant.core :as ig]
            [apple.router :as r]
            [migratus.core :as m])
  (:gen-class))

(defmethod ig/init-key :server/jetty
  [_ {:keys [handler port]}]
  (println (str "\n Server adding at port " port))
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/init-key :apple/app
  [_ config]
  (println "\n App started")
  (r/app config))

(defmethod ig/init-key :db/postgres
  [_ config]
  (println "\n Configured DB")
  (:jdbc-url config))

(defmethod ig/init-key :db/migratus
  [_ config]
  (println "\n Configuring db for migration")
  (:migratus config))

(defmethod ig/halt-key! :server/jetty
 [_ jetty]
 (.stop jetty))

(defn migrate-sql-statements []
  (try
    (m/migrate a/migratus-config)
    (catch Exception e
      (m/rollback a/migratus-config))))

(defn -main
  []
  (ig/init a/config)
  (migrate-sql-statements))