(defsystem "{{projname}}"
  :version "0.0.1"
  :author "TBD"
  :license "TBD"
  :serial t
  :in-order-to ((asdf:test-op (asdf:test-op :{{projname}}/test)))
  :depends-on (:arrows :1am)
  :components ((:module "src"
                :serial t
                :components ((:file "package")
                             (:file "main" :depends-on ("package"))))))

(defsystem :{{projname}}/test
  :depends-on (:{{projname}} :1am)
  :serial t
  :components ((:module "test"
                :serial t
                :components ((:file "package")
                             (:file "main"))))
  :perform (asdf:test-op (op system)
                         (funcall (read-from-string "{{projname}}-tests:run-tests"))))
