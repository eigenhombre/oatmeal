(defsystem "{{projname}}"
  :version "0.0.1"
  :author "TBD"
  :license "TBD"
  :depends-on (:arrows)
  :components ((:module "src"
                :components ((:file "package")
                             (:file "main" :depends-on ("package"))))))
