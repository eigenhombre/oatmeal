(ns oatmeal.core
  (:require [clojure.string :as string]
            [docopt.core :as docopt]
            [oatmeal.fs :as fs]
            [oatmeal.readme :as r])
  (:gen-class))

(def usage
  "
Usage: oatmeal lib <libname>
       oatmeal app <appname>
       oatmeal update-readme

Sources will be created in $OATMEAL_DIR; if not present,
$HOME/common-lisp will be used.
")

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (docopt/docopt usage
                 args
                 (fn [{:strs [update-readme
                              <libname>
                              <appname>] :as argmap}]
                   (cond
                     update-readme (r/update-readme! usage)
                     <libname> (fs/make-lib <libname>)
                     <appname> (fs/make-app <appname>)
                     :else (println usage)))))

(comment
  (-main "newlib" "fooboo")
  (-main "newapp" "fooapp")
  (-main "update-readme"))
