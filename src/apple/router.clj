(ns apple.router
  (:require [reitit.ring :as ring]
            [apple.recipe.routes :as recipe]))

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