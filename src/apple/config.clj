(ns apple.config
  (:require [integrant.core :as ig]
            [environ.core :refer [env]]))

(def db {:dbname   (or (System/getenv "DB_DBNAME") "it_data")
         :user     (or (System/getenv "DB_USER") "myuser")
         :password (or (System/getenv "DB_PASSWORD") "mypassword")
         :host     (or (System/getenv "DB_HOST") "localhost")
         :port     (or (System/getenv "DB_PORT") 5432) 
         :dbtype   "postgresql"})

(def config
  {:server/jetty {:handler (ig/ref :apple/app)
                  :port (or (System/getenv "PORT") 3000)}
   :apple/app {:jdbc-url (ig/ref :db/postgres)}
   :db/postgres {:jdbc-url "jdbc-url"}})