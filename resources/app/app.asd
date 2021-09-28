(defsystem "{{progname}}"
  :build-operation "program-op"
  :build-pathname "{{progname}}"
  :entry-point "{{progname}}:main"
  :depends-on (:arrows)
  :components ((:module "src"
                :components ((:file "package")
                             (:file "main" :depends-on ("package"))))))
