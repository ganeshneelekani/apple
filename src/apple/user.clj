(ns apple.user
  (:require [apple.server] 
            [apple.config :as a] 
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [reitit.core :as reitit]
            [reitit.coercion :as coer]
            [integrant.repl :as ig-repl]
            [reitit.core :as rrouter]
            [integrant.repl.state :as state]
            [reitit.coercion.spec :as rspec]))

(ig-repl/set-prep!
 (fn []
   a/config))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(def app (-> state/system :apple/app))
(def db (-> state/system :db/postgres))
(def conn (-> state/system :db/connection))

(def router
  (reitit.core/router
   ["/v1/recipes/:recipe-id"
    {:coercion rspec/coercion
     :parameters {:path {:recipe-id int?}}}]
   {:compile coer/compile-request-coercers}))

(comment
  (go)
  (coer/coerce!
   (rrouter/match-by-path router "/v1/recipes/1234")
   )
  (app {:request-method :get
        :uri "/v1/recipes/1234"})
  
  (app {:request-method :get
        :uri "/"})
  
  (:require  '[clojure.pprint :refer [pprint]])
  (rrouter/match-by-path router "/v1/recipes/1234-recipe")
  
  (jdbc/execute! db ["select * from recipe where public = true"])

  (sql/find-by-keys db :recipe {:public true})
  (halt)
  (reset)
  
  (reset-all)
  
  (time  {:public (sql/find-by-keys conn :recipe {:public true})
          :drafts (sql/find-by-keys conn :recipe {:public false 
                                                  :uid "auth0|5ef440986e8fbb001355fd9c"})})
  )
