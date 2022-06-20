(defsystem :{{projname}}
  :version "0.0.1"
  :description "FIXME"
  :author "FIXME"
  :license "FIXME"
  :serial t
  :in-order-to ((asdf:test-op (asdf:test-op :{{projname}}/test)))
  :depends-on (:arrows
               ;; FIXME: Though not an explicit run-time dependency,
               ;; for some reason I needed to add this for GitHub
               ;; Action CI build to succeed.  This merits more
               ;; investigation:
               :1am)
  :components ((:module "src"
                :serial t
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
