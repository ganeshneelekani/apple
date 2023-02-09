(ns apple.util 
  (:require [cheshire.core :as json]
            [ring.util.response :as rr]))

(defn create-success-response [result]
  (-> result
      (json/generate-string)
      rr/response
      (rr/header "Content-Type" "application/json; charset=UTF-8")))

(defn create-error-response [result]
  (-> result
      (json/generate-string)
      rr/response
      (rr/header "Content-Type" "application/json; charset=UTF-8")))