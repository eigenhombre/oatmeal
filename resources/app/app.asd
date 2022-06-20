(defsystem "{{projname}}"
  :build-operation "program-op"
  :build-pathname "{{projname}}"
  :entry-point "{{projname}}:main"
  :depends-on (:arrows)
  :components ((:module "src"
                :components ((:file "package")
                             (:file "main" :depends-on ("package"))))))