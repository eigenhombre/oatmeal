(ns oatmeal.fs
  (:require [me.raynes.fs :as fs]
            [clojure.java.io :as io]
            [clostache.parser :as p]))

(defmacro with-tmp-dir [dir-file & body]
  `(let [~dir-file (fs/temp-dir "oatmeal")]
     (try
       ~@body
       (finally
         ;; FIXME: Eliminate double-evaluation:
         (fs/delete-dir ~dir-file)))))

(defn lisp-toplevel-dir [{:keys [oatmeal-dir] :as env}]
  (or oatmeal-dir (str (:home env) "/common-lisp")))

(defn resource-file [path]
  (slurp (io/resource path)))

;; FIXME: Move these elsewhere:
(defn make-lib [env projname]
  (let [tldir (lisp-toplevel-dir env)
        target (str tldir "/" projname)]
    (fs/mkdir target)
    (spit (str target "/Makefile") "")
    (spit (str target "/main.lisp") "(format t \"Hello World~%\")\n")
    (println "LIB" projname "in directory" tldir)))

;; FIXME: Move these elsewhere:
(defn make-app [env projname]
  (let [tldir (lisp-toplevel-dir env)
        target (str tldir "/" projname)]
    (fs/mkdir target)
    (spit (str target "/Makefile")
          (p/render (resource-file "Makefile.app")
                    {:progname projname}))
    (spit (str target "/main.lisp")
          "(defun main () (format t \"Hello World~%\"))\n")
    (spit (str target "/build.sh")
          (p/render (resource-file "build.sh")
                    {:progname projname}))
    (fs/chmod "+x" (str target "/build.sh"))
    (println "APP" projname "in directory" tldir)))
