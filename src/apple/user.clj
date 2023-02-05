(ns apple.user
  (:require [integrant.repl :as ig-repl] 
            [integrant.repl.state :as state]
            [apple.server]
            [apple.config :as a]))

(ig-repl/set-prep!
 (fn []
   a/config))

(def go ig-repl/go)
(def halt ig-repl/halt)
(def reset ig-repl/reset)
(def reset-all ig-repl/reset-all)

(defn app [] (-> state/system :apple/app))
(defn db [] (-> state/system :db/postgres))

(comment
  (go)
  (halt)
  (reset)
  (reset-all)
  )