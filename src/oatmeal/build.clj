(ns oatmeal.build
  (:require [clojure.java.io :as io]
            [clostache.parser :refer [render]]
            [me.raynes.fs :refer [chmod]]
            [oatmeal.fs :as fs])
  (:import [java.nio.file FileAlreadyExistsException]))

(defn render-and-write [projname target-dir target-file src-file]
  (spit (str target-dir "/" target-file)
        (render (fs/resource-file src-file)
                {:projname projname})))

(def ^:dynamic *report-success*
  (fn [projtype projname]
    (println (format "%s %s" projtype projname))))

(defn ^:private show-action [projtype projname]
  (*report-success* projtype projname))

(defmacro make-project
  {:style/indent 1}
  [projname & body]
  `(let [tldir# (fs/*lisp-toplevel-dir*)
         target# (str tldir# "/" ~projname)
         render-file# (partial render-and-write ~projname target#)
         ~'target target#
         ~'render-file render-file#]
     (when (.exists (io/file target#))
       (throw (FileAlreadyExistsException.
               (format "Directory '%s' already exists; not overwriting."
                       target#))))
     (fs/mkdirp target#)
     (fs/mkdirp (str target# "/src"))
     (fs/mkdirp (str target# "/test"))
     (render-file# "test.sh" "common/test.sh")
     (render-file# ".gitignore" "common/gitignore")
     (chmod "+x" (str target# "/test.sh"))
     (render-file# "test/test.lisp" "common/test/test.lisp")
     (render-file# "test/package.lisp" "common/test/package.lisp")
     ~@body))

;; These statements break linting. However, they are mandatory to make
;; the Cursive plugin for the IntelliJ editor work. So, you can
;; uncomment them when changing this code:
#_(declare render-file)
#_(declare target)

(defn make-lib [projname]
  (make-project projname
    (render-file "Makefile" "lib/Makefile")
    (render-file (str projname ".asd") "lib/lib.asd")
    (render-file "src/main.lisp" "lib/src/main.lisp")
    (render-file "src/package.lisp" "lib/src/package.lisp")
    (show-action "LIB" target)))

(defn make-app [projname]
  (make-project projname
    (render-file "Makefile" "app/Makefile")
    (render-file (str projname ".asd") "app/app.asd")
    (render-file "src/main.lisp" "app/main.lisp")
    (render-file "src/package.lisp" "app/package.lisp")
    (render-file "build.sh" "app/build.sh")
    (chmod "+x" (str target "/build.sh"))
    (show-action "APP" target)))
