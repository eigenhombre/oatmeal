(ns oatmeal.fs
  (:require [clojure.java.io :as io]
            [me.raynes.fs :as fs]))

(defmacro with-tmp-dir [dir-file & body]
  `(let [~dir-file (fs/temp-dir "oatmeal")]
     (try
       ~@body
       (finally
         ;; FIXME: Eliminate double-evaluation:
         (fs/delete-dir ~dir-file)))))

(defn lisp-toplevel-dir [{:keys [lisp-home] :as env}]
  (or lisp-home (str (:home env) "/common-lisp")))

(defn resource-file [path]
  (slurp (io/resource path)))

(defn mkdirp [path]
  (io/make-parents (str path "/dummy")))
