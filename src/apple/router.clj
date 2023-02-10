(ns apple.router
  (:require [reitit.ring :as ring]
            [muuntaja.core :as m] 
            [apple.recipe.routes :as recipe]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.coercion.spec :as rspec]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.exception :as exception]))

(def router-config
  {:data {:coercion rspec/coercion 
          :middleware [exception/exception-middleware
                       coercion/coerce-request-middleware
                       ]}})

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
  (->
   (routes env)
   ring/router
   ring/ring-handler))

