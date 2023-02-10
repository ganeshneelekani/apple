(ns apple.recipe.routes
  (:require [apple.recipe.handlers :as handler]))

(defn routes
  [env]
  (println "---env-------"env)
  (let [conn (:connection env)
        db (:jdbc-url env)]
    ["/recipes"
     [""
      {:get {:handler (handler/list-all-recipes conn)
             :summary "List all recipes"}
       :post {:handler (handler/create-recipe! db)
              :summary "Create recipe"}}]
     ["/:recipe-id"
      {:get {:handler (handler/retrieve-recipe conn)
       :parameters {:path {:recipe-id string?}}
             :summary "Retrieve recipe"}}]]))