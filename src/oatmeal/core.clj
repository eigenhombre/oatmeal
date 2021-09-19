(ns oatmeal.core
  (:require [clojure.string :as string]
            [docopt.core :as docopt]
            [oatmeal.fs :as fs]
            [oatmeal.readme :as r])
  (:gen-class))

(def usage
  "
Usage: oatmeal create lib <libname>
       oatmeal create app <appname>
       oatmeal update readme

Sources will be created in \\$OATMEAL_DIR; if not present,
\\$HOME/common-lisp will be used.
")

(defn -main [& args]
  (docopt/docopt usage
                 args
                 (fn [{:strs [update readme
                              <libname>
                              <appname>] :as argmap}]
                   (cond
                     (and update readme) (r/update-readme! usage)
                     <libname> (fs/make-lib <libname>)
                     <appname> (fs/make-app <appname>)
                     :else (println usage)))))

(comment
  (-main "create" "lib" "fooboo")
  (-main "create" "app" "fooapp")
  (-main "update" "readme"))
