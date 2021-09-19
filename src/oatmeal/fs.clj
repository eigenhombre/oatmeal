(ns oatmeal.fs
  (:require [me.raynes.fs :as fs]))

(defmacro with-tmp-dir [dir-file & body]
  `(let [~dir-file (fs/temp-dir "oatmeal")]
     (try
       ~@body
       (finally
         ;; FIXME: Eliminate double-evaluation:
         (fs/delete-dir ~dir-file)))))

(defn lisp-toplevel-dir [{:keys [oatmeal-dir] :as env}]
  (or oatmeal-dir (str (:home env) "/common-lisp")))

(defn make-lib [env libname]
  (let [tldir (lisp-toplevel-dir env)]
    (fs/mkdir (str tldir "/" libname))
    (println "LIB" libname "in directory" tldir)))

(defn make-app [env appname]
  (let [tldir (lisp-toplevel-dir env)]
    (fs/mkdir (str tldir "/" appname))
    (println "APP" appname "in directory" tldir)))

