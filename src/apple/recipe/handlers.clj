(ns apple.recipe.handlers
  (:require [apple.recipe.db :as recipe-db]
            [ring.util.response :as rr]
            [cheshire.core :as json]
            [apple.util :as util])
  (:import (java.util UUID)))

(def base-url "https://api.apple.com")

(defn list-all-recipes
  [conn]
  (fn [request]
    (let [uid "auth0|5ef440986e8fbb001355fd9c"
          recipes (recipe-db/find-all-recipes conn uid)]
      (rr/response recipes))))

(defn create-recipe!
  [db]
  (fn [request]
    (let [recipe-id (str (UUID/randomUUID))
          uid "auth0|5ef440986e8fbb001355fd9c"
          recipe (-> request :body-params)]
      (recipe-db/insert-recipe! db (assoc recipe :recipe-id recipe-id :uid uid))
      (rr/created (str base-url "/recipes/" recipe-id) {:recipe-id recipe-id}))))

(defn retrieve-recipe
  [conn]
  (fn [request]
    (let [recipe-id "a3dde84c-4a33-45aa-b0f3-4bf9ac997680"
          recipe (recipe-db/find-recipe-by-id conn recipe-id)]
      (if recipe
        (rr/response recipe)
        (rr/not-found {:type "recipe-not-found"
                       :message "Recipe not found"
                       :data (str "recipe-id " recipe-id)})))))