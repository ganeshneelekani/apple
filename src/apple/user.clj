(ns apple.user
  (:require [integrant.repl :as ig-repl] 
            [integrant.repl.state :as state]
            [apple.server]
            [apple.config :as a]
            [next.jdbc :as jdbc]
            [next.jdbc.sql :as sql]))

(ig-repl/set-prep!
 (fn []
   a/config))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(def app (-> state/system :apple/app))
(def db (-> state/system :db/postgres))


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
  
  (time 
   (with-open [conn (jdbc/get-connection db)]
     {:public (sql/find-by-keys conn :recipe {:public true})
      :drafts (sql/find-by-keys conn :recipe {:public false 
                                              :uid "auth0|5ef440986e8fbb001355fd9c"})}))
  )

(comment
  
  (load-file "recipe/db")
  (load-file "recipe/handlers")
  (load-file "recipe/routes")
  (load-file "src/apple/config")
  (load-file "router")
  (load-file "server")
  
  (doseq [file (->> "src"
                   (io/file)
                   (io/list-files)
                   (filter #(.endsWith % ".clj"))
                   (map io/file))]
    (load-file (.getAbsolutePath file)))
  )