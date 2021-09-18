.PHONY: all

BINPATH = ${HOME}/bin
JARPATH = target/uberjar/oatmeal.jar
all: ${JARPATH}

${JARPATH}: src/oatmeal/*.clj
	lein uberjar

clean:
	rm -rf target/*

install:
	mkdir -p ${BINPATH}
	cp ${JARPATH} ${BINPATH}

doc: ${JARPATH}
	java -jar ${JARPATH} make-readme
