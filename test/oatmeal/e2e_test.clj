(ns oatmeal.e2e-test
  (:require [clojure.string :as string]
            [clojure.test :refer [deftest testing is are]]
            [oatmeal.core :refer [execute-cmd]]
            [oatmeal.fs :as fs]
            [clojure.java.io :as io]
            [clojure.java.shell :as shell]))

(defn cmd [env s]
  (execute-cmd env (string/split s #"\s+")))

(deftest e2etests-common
  (doseq [kind [:lib :app]]
    (testing (str "Making a new " (name kind) "project")
      (fs/with-tmp-dir d
        (testing "It should create a directory called `foo`"
          (cmd {:oatmeal-dir (str d)} (str "create " (name kind) " foo"))
          (testing "The project directory exists"
            (is (.exists (io/file (str d "/foo")))))
          (testing "There is a Makefile"
            (is (.exists (io/file (str d "/foo/Makefile")))))
          (testing "There is a main.lisp"
            (is (.exists (io/file (str d "/foo/main.lisp")))))
          (when (= kind :app)
            (testing "There is a build.sh"
              (is (.exists (io/file (str d "/foo/build.sh")))))
            (testing "Building it doesn't barf"
              (let [{:keys [exit out err]}
                    (shell/sh "make" :dir (str d "/foo"))]
                (testing "Make succeeded"
                  (is (zero? exit))
                  (is (seq out))
                  (is (empty? err)))))
            (testing "Running it works"
              (let [{:keys [exit out err]}
                    (shell/sh "./foo" :dir (str d "/foo"))]
                (testing "Execution succeeded"
                  (is (zero? exit))
                  (is (seq out))
                  (is (empty? err)))))
            ;; YAH: Next item: `make clean`
            ))
        (testing "Just creating the project files, but in a deeply nested path"
          (cmd {:oatmeal-dir (str d "/a/nested/sub/directory")}
               (str "create " (name kind) " foo")))))))
