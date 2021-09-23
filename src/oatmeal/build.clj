(ns oatmeal.build
  (:require [me.raynes.fs :refer [chmod]]
            [clostache.parser :refer [render]]
            [oatmeal.fs :as fs]))

(defn make-lib [env projname]
  (let [tldir (fs/lisp-toplevel-dir env)
        target (str tldir "/" projname)]
    (fs/mkdirp target)
    (spit (str target "/Makefile") "")
    (spit (str target "/main.lisp") "(format t \"Hello World~%\")\n")
    (spit (str target "/package.lisp")
          (render (fs/resource-file "package.lisp")
                  {:progname projname}))
    (println "LIB" projname "in directory" tldir)))

(defn make-app [env projname]
  (let [tldir (fs/lisp-toplevel-dir env)
        target (str tldir "/" projname)
        render-w-projname (fn [outfile]
                            (spit (str target "/" outfile)
                                  (render (fs/resource-file outfile)
                                          {:progname projname})))]
    (fs/mkdirp target)
    (spit (str target "/Makefile")
          (render (fs/resource-file "Makefile.app")
                  {:progname projname}))
    (doseq [f ["main.lisp"
               "build.sh"
               "package.lisp"]]
      (render-w-projname f))
    (chmod "+x" (str target "/build.sh"))
    (println "APP" projname "in directory" tldir)))
