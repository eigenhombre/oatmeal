.PHONY: clean install

{{progname}}: src/*.lisp
	./build.sh

clean:
	rm -rf {{progname}}

install: {{progname}}
	test -n "$(BINDIR)"  # $$BINDIR
	cp {{progname}} ${BINDIR}
