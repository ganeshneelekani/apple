(ns apple.recipe.handlers
  (:require [apple.recipe.db :as recipe-db]
            [ring.util.response :as rr]))

(defn list-all-recipes
  [db]
  (fn [request]
    (println "----T-------"db)
    (let [recipes (recipe-db/find-all-recipes db)
          _ (println "----recipes1----------"(rr/response recipes))]
      (rr/response recipes))))