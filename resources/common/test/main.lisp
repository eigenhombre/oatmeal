(in-package #:{{projname}}-tests)

(defun run-tests () (1am:run))

(test example
  (is (equal 2 (+ 1 1))))
