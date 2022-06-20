(ns oatmeal.e2e-test
  (:require [clojure.java.io :as io]
            [clojure.java.shell :as shell]
            [clojure.string :as string]
            [clojure.test :refer [deftest testing is]]
            [me.raynes.fs :as rfs]
            [oatmeal.core :refer [execute-cmd]]
            [oatmeal.fs :refer [mkdirp]]
            [oatmeal.fs :as fs])
  (:import [java.nio.file FileAlreadyExistsException]))

(defn- oatmeal-cmd [env s]
  (execute-cmd env (string/split s #"\s+")))

(defn- exists-in-dir [dir path-in-dir]
  (->> (str dir path-in-dir)
       io/file
       .exists))

(defn sbcl-with-ql-context [dirname & args]
  (apply shell/sh
         (concat ["sbcl"
                  "--non-interactive"
                  "--disable-debugger"
                  "--eval"
                  "(pushnew (truename \".\") ql:*local-project-directories*)"
                  "--eval"
                  "(ql:register-local-projects)"]
                 args
                 [:dir dirname])))

(deftest e2etests-common
  (doseq [kind [:lib :app]]
    (testing (str "Making a new " (name kind) " project")
      (fs/with-tmp-dir d
        (let [exists (partial exists-in-dir d)]
          (testing "The directory already exists"
            (mkdirp (str d "/baz"))
            (testing "... exception is thrown"
              (is (thrown? FileAlreadyExistsException
                    (oatmeal-cmd {:oatmeal-dir (str d)}
                                 (str "create " (name kind) " baz"))))))
          (testing "It should create a directory called `foo`"
            (oatmeal-cmd {:oatmeal-dir (str d)}
                         (str "create " (name kind) " foo"))
            (testing "The project directory exists"
              (is (exists "/foo")))
            (testing "There is a Makefile"
              (is (exists "/foo/Makefile")))
            (testing "There is a main.lisp"
              (is (exists "/foo/src/main.lisp")))
            (testing "There is a package.lisp"
              (is (exists "/foo/src/package.lisp")))
            (testing "There is an ASDF file"
              (is (exists "/foo/foo.asd")))
            (testing "Project is loadable through Quicklisp"
              (let [{:keys [exit out err]}
                    (sbcl-with-ql-context (str d "/foo")
                                          "--eval"
                                          "(ql:quickload :foo)")]
                (testing "quickload succeeded"
                  (is (zero? exit))
                  (is (seq out))
                  (is (empty? err)))))
            (testing "Project is testable through ASDF"
              (let [{:keys [exit out err]}
                    (sbcl-with-ql-context (str d "/foo")
                                          "--eval"
                                          "(asdf:test-system :foo)")]
                (testing "tests succeeded"
                  (is (zero? exit))
                  (is (seq out))
                  (is (empty? err)))))
            (testing "Makefile"
              (testing "It exists"
                (is (exists "/foo/Makefile")))
              (testing "test script exists"
                (is (exists "/foo/test.sh"))
                (testing "It has the execute bit set"
                  (is (-> d
                          (str "/foo/test.sh")
                          clojure.java.io/file
                          .canExecute))))
              (testing "`make test`"
                (let [{:keys [exit out err]}
                      (shell/sh "make" "test" :dir (str d "/foo"))]
                  (testing "Make succeeded"
                    (is (zero? exit))
                    (is (seq out))
                    (is (empty? err))))))
            (when (= kind :app)
              (testing "There is a build.sh"
                (is (exists "/foo/build.sh")))
              (testing "Building it doesn't barf"
                (let [{:keys [exit out err]}
                      (shell/sh "make" :dir (str d "/foo"))]
                  (testing "Make succeeded"
                    (is (zero? exit))
                    (is (seq out))
                    (is (empty? err))
                    (testing "It built an executable"
                      (is (exists "/foo/foo"))))))
              (testing "Running it works"
                (let [{:keys [exit out err]}
                      (shell/sh "./foo" :dir (str d "/foo"))]
                  (testing "Execution succeeded"
                    (is (zero? exit))
                    (is (seq out))
                    (is (empty? err)))))
              (testing "`make install`"
                (fs/with-tmp-dir bindir
                  (let [{:keys [exit err]}
                        (shell/sh "make" "install"
                                  :dir (str d "/foo")
                                  :env {"BINDIR" (str bindir)})]
                    (testing "Execution succeeded"
                      (is (zero? exit))
                      (is (empty? err)))
                    (let [foofile (io/file (str bindir "/foo"))]
                      (testing "Target executable exists"
                        (is (.exists foofile))
                        (testing "and is executable"
                          (is (rfs/executable? foofile))))))))
              (testing "`make clean`"
                (let [{:keys [exit err]}
                      (shell/sh "make" "clean"
                                :dir (str d "/foo"))]
                  (testing "It succeeded"
                    (is (zero? exit))
                    (is (empty? err))
                    (is (not (exists "/foo/foo")))))))))
        (testing "Just creating the project files, but in a deeply nested path"
          (oatmeal-cmd {:oatmeal-dir (str d "/a/nested/sub/directory")}
                       (str "create " (name kind) " foo")))))))
