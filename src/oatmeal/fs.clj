(ns oatmeal.fs
  (:require [me.raynes.fs :as fs]))

(defmacro with-tmp-dir [dir-file & body]
  `(let [~dir-file (fs/temp-dir "oatmeal")]
     (try
       ~@body
       (finally
         ;; FIXME: Eliminate double-evaluation:
         (fs/delete-dir ~dir-file)))))

(defn make-lib [libname]
  (println "LIB" libname))

(defn make-app [appname]
  (println "APP" appname))

