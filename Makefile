.PHONY: all
.PHONY: docker
.PHONY: test

BINPATH = ${HOME}/bin
JARPATH = target/uberjar/oatmeal.jar

${JARPATH}: src/oatmeal/*.clj project.clj resources/*
	lein uberjar

clean:
	rm -rf target/*

install:
	mkdir -p ${BINPATH}
	cp ${JARPATH} ${BINPATH}

doc: ${JARPATH}
	java -jar ${JARPATH} update readme

all: ${JARPATH} doc install

docker:
	docker build -t "oatmeal" .

test:
	lein do test, bikeshed, kibit
