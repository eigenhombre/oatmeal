#!/bin/sh

# Adapted from
# https://github.com/cicakhq/potato/blob/master/tools/build_binary.sh
sbcl --non-interactive \
     --disable-debugger \
     --load main.lisp \
     --eval '(progn (sb-ext:disable-debugger) (sb-ext:save-lisp-and-die "{{progname}}" :toplevel #'"'"'main :executable t))'
