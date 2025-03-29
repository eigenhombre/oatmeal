FROM debian

RUN apt-get update
RUN apt-get install -y make wget bzip2 leiningen sbcl build-essential texinfo curl
RUN apt-get clean

RUN wget https://sourceforge.net/projects/sbcl/files/sbcl/2.5.2/sbcl-2.5.2-source.tar.bz2 -O /tmp/sbcl-source.tar.bz2 && \
    mkdir /tmp/sbcl && \
    tar jxvf /tmp/sbcl-source.tar.bz2 -C /tmp/sbcl/ && \
    cd /tmp/sbcl/sbcl-2.5.2 && \
    sh make.sh && \
    sh install.sh

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
RUN lein uberjar
