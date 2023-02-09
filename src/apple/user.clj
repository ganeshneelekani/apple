(ns apple.user
  (:require [integrant.repl :as ig-repl] 
            [integrant.repl.state :as state] 
            [apple.config :as a]
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]
            [apple.server]))

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


(comment
  (go)
  (app {:request-method :get
        :uri "/v1/recipes"})
  
  (app {:request-method :get
        :uri "/"})
  (jdbc/execute! db ["select * from recipe where public = true"])

  (sql/find-by-keys db :recipe {:public true})
  (halt)
  (reset)
  (reset-all)
  
  (time  {:public (sql/find-by-keys conn :recipe {:public true})
          :drafts (sql/find-by-keys conn :recipe {:public false 
                                                  :uid "auth0|5ef440986e8fbb001355fd9c"})})
  )
