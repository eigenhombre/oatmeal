.PHONY: clean install test

{{projname}}: src/*.lisp
	./build.sh

test:
	./test.sh

clean:
	rm -rf {{projname}}

install: {{projname}}
	test -n "$(BINDIR)"  # $$BINDIR
	cp {{projname}} ${BINDIR}
