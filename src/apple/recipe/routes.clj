(ns apple.recipe.routes
  (:require [apple.recipe.handlers :as handler]))

(defn routes
  [env]
  (let [conn (:connection env)]
    ["/recipes"
     [""
      {:get {:handler (handler/list-all-recipes conn)
             :summary "List all recipes"}}]
     ["/:recipe-id"
      {:get {:handler (handler/retrieve-recipe conn)
       :parameters {:path {:recipe-id string?}}
             :summary "Retrieve recipe"}}]]))