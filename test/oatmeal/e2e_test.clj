(ns oatmeal.e2e-test
  (:require [clojure.test :refer [deftest testing is are]]
            [oatmeal.core :refer [-main]]
            [clojure.string :as string]))

(defn cmd [s]
  (apply -main (string/split s)))

(deftest e2etests
  (testing "Making a new app"
    ;; ... WIP
    ))
