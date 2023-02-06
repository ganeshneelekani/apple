(ns apple.recipe.db 
  (:require [next.jdbc.sql :as sql]))

(defn find-all-recipes
  [db]
  (println "======T1`=========")
  (let [public (sql/find-by-keys db :recipe {:public true})
        _ (println "-----PP-----"public)]
    {:public public}))