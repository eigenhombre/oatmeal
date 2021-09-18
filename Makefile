
BINPATH = ${HOME}/bin

all: target/uberjar/oatmeal.jar

target/uberjar/oatmeal.jar: src/oatmeal/*.clj
	lein uberjar

clean:
	rm -rf target/*

install:
	mkdir -p ${BINPATH}
	cp target/uberjar/oatmeal.jar ${BINPATH}

