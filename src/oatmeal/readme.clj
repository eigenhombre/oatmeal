(ns oatmeal.readme
  (:require [clojure.string :as string]))

(defn readme-txt [orig-txt usage]
  (string/replace orig-txt
                  #"(?sm)\# BEGIN OATMEAL USAGE.+?END OATMEAL"
                  (string/join "\n" ["# BEGIN OATMEAL USAGE"
                                     "#+BEGIN_SRC"
                                     usage
                                     "#+END_SRC"
                                     "# END OATMEAL USAGE"])))

(defn update-readme! [usage]
  (let [orig-txt (slurp "README.org")]
    (spit "README.org" (readme-txt orig-txt usage))))

