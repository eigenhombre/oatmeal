(ns oatmeal.core
  (:require [clojure.string :as string]
            [docopt.core :as docopt])
  (:gen-class))

(def usage
  "
Usage: oatmeal [options]
       oatmeal make-readme
")

(defn readme-txt [orig-txt]
  (string/replace orig-txt
                  #"(?ms)\# BEGIN OATMEAL.*END OATMEAL"
                  (string/join "\n" ["# BEGIN OATMEAL"
                                     "#+BEGIN_SRC"
                                     usage
                                     "#+END_SRC"
                                     "# END OATMEAL"])))

(defn replace-usage []
  (let [orig-txt (slurp "README.org")]
    (spit "README.org" (readme-txt orig-txt))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (docopt/docopt usage
                 args
                 (fn [{:strs [make-readme] :as argmap}]
                   (when make-readme
                     (replace-usage)))))

