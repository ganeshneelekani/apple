(ns apple.recipe.handlers
  (:require [apple.recipe.db :as recipe-db]
            [ring.util.response :as rr]
            [cheshire.core :as json]))



(defn list-all-recipes
  [db]
  (fn [request]
    (let [uid "auth0|5ef440986e8fbb001355fd9c"
          recipes (recipe-db/find-all-recipes db uid)]
      (-> recipes
          (json/generate-string)
          rr/response
          (rr/header "Content-Type" "application/json; charset=UTF-8")))))