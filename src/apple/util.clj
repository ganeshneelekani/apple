(ns apple.util 
  (:require [cheshire.core :as json]
            [ring.util.response :as rr]))

(defn success-response [result]
  (-> result
      (json/generate-string)
      rr/response
      (rr/header "Content-Type" "application/json; charset=UTF-8")))

(defn create-response [url result]
  (-> result
      (json/generate-string)
      (rr/created url)
      (rr/header "Content-Type" "application/json; charset=UTF-8")))

(defn error-response [result]
  (-> result
      (json/generate-string)
      rr/response
      (rr/header "Content-Type" "application/json; charset=UTF-8")))