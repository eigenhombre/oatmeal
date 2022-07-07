FROM adoptopenjdk:11-jre-hotspot

RUN apt-get -y update
RUN apt-get -y upgrade
RUN apt-get install -y make sbcl leiningen

WORKDIR /home/janice

# Pull down Quicklisp and install it
RUN curl -s -o quicklisp.lisp http://beta.quicklisp.org/quicklisp.lisp
RUN sbcl --no-sysinit --no-userinit --load quicklisp.lisp \
         --eval '(quicklisp-quickstart:install :path "/home/janice/quicklisp")' \
         --quit

# Set up .sbcl to load it:
RUN echo | sbcl --load /home/janice/quicklisp/setup.lisp --eval '(ql:add-to-init-file)' --quit

# Smoke test of Quicklisp:
RUN sbcl --non-interactive \
         --disable-debugger \
         --eval '(ql:quickload :cl-aa)'

# Set up Oatmeal:
WORKDIR /home/janice/oatmeal

COPY . /home/janice/oatmeal
RUN make test
