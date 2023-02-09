(ns apple.recipe.routes
  (:require [apple.recipe.handlers :as handler]))

(defn routes
  [env]
  (let [db (:jdbc-url env)]
    ["/recipes" {:get {:handler (handler/list-all-recipes db)}}]))