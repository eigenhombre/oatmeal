#!/bin/sh

# Adapted from
# https://github.com/cicakhq/potato/blob/master/tools/build_binary.sh
sbcl --non-interactive \
     --disable-debugger \
     --load src/package.lisp \
     --load src/main.lisp \
     --eval '(progn (sb-ext:disable-debugger) (sb-ext:save-lisp-and-die "{{progname}}" :toplevel #'"'"'{{progname}}:main :executable t))'
