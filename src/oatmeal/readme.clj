(ns oatmeal.readme
  (:require [clojure.string :as string]))

(defn readme-txt [orig-txt usage]
  (string/replace orig-txt
                  #"(?ms)\# BEGIN OATMEAL.*END OATMEAL"
                  (string/join "\n" ["# BEGIN OATMEAL"
                                     "#+BEGIN_SRC"
                                     usage
                                     "#+END_SRC"
                                     "# END OATMEAL"])))

(defn update-readme! [usage]
  (let [orig-txt (slurp "README.org")]
    (spit "README.org" (readme-txt orig-txt usage))))

