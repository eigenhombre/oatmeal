#!/bin/sh

# Adapted from
# https://github.com/cicakhq/potato/blob/master/tools/build_binary.sh
sbcl --non-interactive \
     --disable-debugger \
     --eval '(pushnew (truename ".") ql:*local-project-directories*)' \
     --eval '(ql:register-local-projects)' \
     --eval '(ql:quickload :{{progname}})' \
     --eval '(progn (sb-ext:disable-debugger) (sb-ext:save-lisp-and-die "{{progname}}" :toplevel #'"'"'{{progname}}:main :executable t))'
