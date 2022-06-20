.PHONY: clean install

{{projname}}: src/*.lisp
	./build.sh

test:
	echo OK

clean:
	rm -rf {{projname}}

install: {{projname}}
	test -n "$(BINDIR)"  # $$BINDIR
	cp {{projname}} ${BINDIR}
