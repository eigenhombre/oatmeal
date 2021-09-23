(ns oatmeal.core
  (:require [clojure.string :as string]
            [docopt.core :as docopt]
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
")

(defn execute-cmd [env args]
  (docopt/docopt usage
                 args
                 (fn [{:strs [update readme
                              <libname>
                              <appname>] :as argmap}]
                   (cond
                     (and update readme) (r/update-readme! usage)
                     <libname> (b/make-lib env <libname>)
                     <appname> (b/make-app env <appname>)
                     :else (println usage)))))

(defn -main [& args]
  (execute-cmd env/env args))

(comment
  (-main "create" "lib" "fooboo")
  (-main "create" "app" "fooapp")
  (-main "update" "readme"))
