(ns oatmeal.fs
  (:require [clojure.java.io :as io]
            [me.raynes.fs :as fs])
  (:import (java.io File)))

(defmacro with-tmp-dir [dir-file & body]
  `(let [^File ~dir-file (fs/temp-dir "oatmeal")]
     (try
       ~@body
       (finally
         ;; FIXME: Eliminate double-evaluation:
         (fs/delete-dir ~dir-file)))))

(def ^:dynamic *lisp-toplevel-dir* (fn [] (System/getProperty "user.dir")))

(defn resource-file [path]
  (slurp (io/resource path)))

(defn mkdirp [path]
  (io/make-parents (str path "/dummy")))
