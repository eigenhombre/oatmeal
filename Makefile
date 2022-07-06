.PHONY: all docker test

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

all: test ${JARPATH} doc install

docker:
	docker build --progress plain -t "oatmeal" .

test:
#       Switch to `lein test` if test fails in CI and you need more info:
	#lein test
	lein do test, bikeshed, kibit, eastwood
