(defsystem :{{projname}}
  :description "FIXME"
  :author "FIXME"
  :license "FIXME"
  :build-operation "program-op"
  :build-pathname "{{projname}}"
  :entry-point "{{projname}}:main"
  :depends-on (:arrows)
  :components ((:module "src"
                :components ((:file "package")
                             (:file "main" :depends-on ("package"))))))

(defsystem :{{projname}}/test
  :description "FIXME"
  :author "FIXME"
  :license "FIXME"
  :depends-on (:{{projname}} :1am)
  :serial t
  :components ((:module "test"
                :serial t
                :components ((:file "package")
                             (:file "test"))))
  :perform (asdf:test-op (op system)
                         (funcall (read-from-string "{{projname}}.test:run-tests"))))
