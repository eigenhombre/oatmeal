FROM adoptopenjdk:11-jre-hotspot

RUN apt-get -qq -y update
RUN apt-get -qq -y upgrade

RUN apt-get install -qq -y leiningen sbcl make

WORKDIR /oatmeal

COPY project.clj /oatmeal/project.clj
RUN lein do clean, deps

COPY . /oatmeal
RUN make test
