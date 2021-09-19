(ns oatmeal.fs-test
  (:require [clojure.java.shell :as shell]
            [clojure.test :refer [deftest testing is]]
            [clojure.java.io :as io]
            [oatmeal.fs :as fs]
            [clojure.string :as string]))

(deftest with-tmp-dir
  (testing "It creates a file object"
    (is (instance? java.io.File
                   (fs/with-tmp-dir d
                     d))))
  (testing "It contains `oatmeal` in the path"
    (is (string/includes? (fs/with-tmp-dir d
                            (:out (shell/sh "pwd" :dir d)))
                          "oatmeal")))
  (testing "It cleans up after itself"
    (let [path (fs/with-tmp-dir d
                 (string/trim (:out (shell/sh "pwd" :dir d))))]
      (is (not
           (.exists (io/file path)))))))
