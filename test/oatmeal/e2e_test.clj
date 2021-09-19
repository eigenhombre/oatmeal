(ns oatmeal.e2e-test
  (:require [clojure.string :as string]
            [clojure.test :refer [deftest testing is are]]
            [oatmeal.core :refer [execute-cmd]]
            [oatmeal.fs :as fs]
            [clojure.java.io :as io]))

(defn cmd [env s]
  (execute-cmd env (string/split s #"\s+")))

(deftest e2etests
  (testing "Making a new library"
    (fs/with-tmp-dir d
      (testing "It should create a directory called `foo`"
        (cmd {:oatmeal-dir (str d)} "create lib foo")
        (is (.exists (io/file (str d "/foo")))))))
  (testing "Making a new app"
    (fs/with-tmp-dir d
      (testing "It should create a directory called `bar`"
        (cmd {:oatmeal-dir (str d)} "create app bar")
        (is (.exists (io/file (str d "/bar"))))))))
