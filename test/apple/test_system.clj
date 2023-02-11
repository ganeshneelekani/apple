(ns apple.test-system
  (:require [clojure.test :refer :all]
            [integrant.repl.state :as state]
            [ring.mock.request :as mock]
            [muuntaja.core :as m]
            ))

(defn test-endpoint
  ([method uri]
   (test-endpoint method uri nil))
  ([method uri opts]
   (let [app (-> state/system :apple/app) 
         request (app (-> (mock/request method uri)
                          (cond-> opts (mock/json-body opts))))]
     (update request :body (partial m/decode "application/json")))))

(comment
  (let [request         (test-endpoint :get "/v1/recipes")
        decoded-request (m/decode-response-body request)]
    (assoc request :body decoded-request))
  (test-endpoint :post "/v1/recipes" {:img       "string"
                                      :name      "my name"
                                      :prep-time 30}))


