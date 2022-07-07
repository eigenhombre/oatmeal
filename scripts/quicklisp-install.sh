#!/usr/bin/env bash

pushd $HOME > /dev/null 2>&1

if [[ -e quicklisp/setup.lisp ]]; then
    echo "Quicklisp already seems to be installed."
else
    curl -s -o /tmp/quicklisp.lisp http://beta.quicklisp.org/quicklisp.lisp
    sbcl --no-sysinit               \
         --no-userinit              \
         --load /tmp/quicklisp.lisp \
         --eval '(quicklisp-quickstart:install :path "quicklisp")' \
         --quit
    sbcl --load quicklisp/setup.lisp --eval '(ql-util:without-prompting (ql:add-to-init-file))' --quit
fi

popd > /dev/null 2>&1
