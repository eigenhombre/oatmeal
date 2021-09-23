FROM adoptopenjdk:11-jre-hotspot

RUN apt-get -y update
RUN apt-get -y upgrade

RUN apt-get install -y leiningen
RUN apt-get install -y sbcl
RUN apt-get install -y make

WORKDIR /oatmeal

COPY project.clj /oatmeal/project.clj
RUN lein do clean, deps

COPY . /oatmeal
RUN make test
