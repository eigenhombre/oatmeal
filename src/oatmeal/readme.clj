(ns oatmeal.readme
  (:require [clojure.string :as string]))

(defn readme-txt [orig-txt usage]
  (string/replace orig-txt
                  #"(?sm)\<!-- BEGIN OATMEAL USAGE -->.+?END OATMEAL USAGE -->"
                  (string/join "\n" ["<!-- BEGIN OATMEAL USAGE -->"
                                     "```"
                                     usage
                                     "```"
                                     "<!-- END OATMEAL USAGE -->"])))

(defn update-readme! [usage]
  (let [orig-txt (slurp "README.md")]
    (spit "README.md" (readme-txt orig-txt usage))))
