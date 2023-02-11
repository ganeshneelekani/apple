(ns apple.router
  (:require [muuntaja.core :as m]
            [reitit.ring :as ring]
            [reitit.dev.pretty :as pretty]
            [reitit.coercion.spec :as rspec]
            [apple.recipe.routes :as recipe]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.multipart :as multipart]
            [reitit.ring.middleware.exception :as exception]))

(def router-config
  {:exception pretty/exception
   :data {:coercion rspec/coercion 
          :muuntaja m/instance
          :middleware [muuntaja/format-negotiate-middleware 
                       muuntaja/format-response-middleware 
                       (exception/create-exception-middleware
                        {::exception/default (partial exception/wrap-log-to-console exception/default-handler)})
                       muuntaja/format-request-middleware
                       coercion/coerce-response-middleware 
                       coercion/coerce-request-middleware 
                       multipart/multipart-middleware]}})

(defn routes
  [env]
  [["/"
     {:get {:handler (fn [req]
                       {:status 200
                        :body "Hello Apple"})}}]
   ["/v1"
    (recipe/routes env)]])

(defn app
  [env]
  (ring/ring-handler
   (ring/router 
    (routes env)
    router-config)))

