(ns oatmeal.core
  (:require [docopt.core :as docopt]
            [oatmeal.build :as b]
            [oatmeal.readme :as r])
  (:gen-class))

(def usage
  "
Usage: oatmeal create lib <libname>
       oatmeal create app <appname>
       oatmeal update readme

Sources will be created in a subdirectory of the current working
directory. If the directory `./<libname|appname>` already
exists, we will not overwrite its contents.

For \"make install\" to work correctly, set an environment BINDIR for
executable files to be placed in.
")

(defn execute-cmd [args]
  (docopt/docopt usage
                 args
                 (fn [{:strs [update readme
                              <libname>
                              <appname>]}]
                   (cond
                     (and update readme) (r/update-readme! usage)
                     <libname> (b/make-lib <libname>)
                     <appname> (b/make-app <appname>)
                     :else (println usage)))))

(defn -main [& args]
  (try
    (execute-cmd args)
    (catch Exception e
      (println (.getMessage e))
      (System/exit -1))))

(comment
  (execute-cmd ["create" "lib" "fooboo"])
  (execute-cmd ["create" "app" "fooapp"])
  (execute-cmd ["update" "readme"]))
