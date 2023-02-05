(ns apple.server
  (:require [apple.config :as a]
            [reitit.ring :as ring]
            [ring.adapter.jetty :as jetty]
            [integrant.core :as ig])
  (:gen-class))

(defn app
  [env]
  (ring/ring-handler
   (ring/router
    [["/"
      {:get {:handler (fn [req]
                        {:status 200
                         :body "Hello Apple"})}}]])))

(defmethod ig/init-key :server/jetty
  [_ {:keys [handler port]}]
  (println (str "\nServer adding at port " port))
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/init-key :apple/app
  [_ config]
  (println "\nApp started")
  (app config))

(defmethod ig/init-key :db/postgres
  [_ config]
  (println "\nConfigured DB")
  (:jdbc-url config))

(defmethod ig/halt-key! :server/jetty
 [_ jetty]
 (.stop jetty))

(defn -main
  []
  (ig/init a/config))

(comment
  )