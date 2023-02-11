(ns apple.recipe.routes
  (:require [apple.recipe.handlers :as handler]
            [apple.responses :as responses]))

(defn routes
  [env]
  (let [conn (:connection env)
        db   (:jdbc-url env)]
    ["/recipes"
     [""
      {:get  {:handler   (handler/list-all-recipes conn)
              :summary   "List all recipes"
              :responses {200 {:body responses/recipes}}}
       :post {:handler (handler/create-recipe! db)
              :summary "Create recipe"}}]
     ["/:recipe-id"
      {:get    {:handler    (handler/retrieve-recipe conn)
                :parameters {:path {:recipe-id string?}}
                :responses  {200 {:body responses/recipe}}
                :summary    "Retrieve recipe"}
       :put    {:handler    (handler/update-recipe! db) 
                :responses  {204 {:body nil?}}
                :summary    "Update recipe"}
       :delete {:handler    (handler/delete-recipe! db)
                :parameters {:path {:recipe-id string?}}
                :responses  {204 {:body nil?}}
                :summary    "Delete recipe"}}
      ]]))