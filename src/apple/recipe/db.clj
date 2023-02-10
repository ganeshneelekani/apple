(ns apple.recipe.db 
  (:require [next.jdbc.sql :as sql]
            [next.jdbc :as jdbc]))

(defn find-all-recipes
  [conn uid]
  (let [public (sql/find-by-keys conn :recipe {:public true})]
    (if uid
      (let [drafts (sql/find-by-keys conn :recipe {:public false :uid uid})]
        {:public public
         :drafts drafts})
      {:public public})))

(defn insert-recipe!
  [db {:keys [recipe-id uid name prep-time img]}]
  (println "---EE-----")
  (sql/insert! db :recipe {:recipe_id recipe-id
                           :uid uid
                           :name name
                           :prep_time prep-time
                           :public false
                           :img img
                           :favorite_count 0}))

(defn find-recipe-by-id
  [conn recipe-id]
  (let [[recipe] (sql/find-by-keys conn :recipe {:recipe_id recipe-id})
        steps (sql/find-by-keys conn :step {:recipe_id recipe-id})
        ingredeints (sql/find-by-keys conn :ingredient {:recipe_id recipe-id})]
    (when (seq recipe)
      (assoc recipe
             :recipe/steps steps
             :recipe/ingredients ingredeints))))


