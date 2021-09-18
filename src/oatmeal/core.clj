(ns oatmeal.core
  (:require [clojure.string :as string])
  (:gen-class))

(defn usage []
  "Usage: eat your oatmeal every day")

(defn readme-txt [orig-txt]
  (let [usage-txt (usage)]
    (string/replace orig-txt
                    #"(?ms)\# BEGIN OATMEAL.*END OATMEAL"
                    (string/join "\n" ["# BEGIN OATMEAL"
                                       usage-txt
                                       "# END OATMEAL"]))))

(defn replace-usage []
  (let [orig-txt (slurp "README.org")]
    (spit "README.org" (readme-txt orig-txt))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (replace-usage))
