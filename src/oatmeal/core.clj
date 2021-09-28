(ns oatmeal.core
  (:require [docopt.core :as docopt]
            [environ.core :as env]
            [oatmeal.build :as b]
            [oatmeal.readme :as r])
  (:gen-class))

(def usage
  "
Usage: oatmeal create lib <libname>
       oatmeal create app <appname>
       oatmeal update readme

Sources will be created in directory specified by the environment
variable OATMEAL_DIR; if not present, a directory \"common-lisp\" in
the user's home directory will be used.

For \"make install\" to work correctly, set an environment BINDIR for
executable files to be placed in.
")

(defn execute-cmd [env args]
  (docopt/docopt usage
                 args
                 (fn [{:strs [update readme
                              <libname>
                              <appname>]}]
                   (cond
                     (and update readme) (r/update-readme! usage)
                     <libname> (b/make-lib env <libname>)
                     <appname> (b/make-app env <appname>)
                     :else (println usage)))))

(defn -main [& args]
  (try
    (execute-cmd env/env args)
    (catch Exception e
      (println (.getMessage e))
      (System/exit -1))))

(comment
  (execute-cmd env/env ["create" "lib" "fooboo"])
  (execute-cmd env/env ["create" "app" "fooapp"])
  (execute-cmd env/env ["update" "readme"]))
