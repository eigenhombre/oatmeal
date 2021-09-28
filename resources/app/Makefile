.PHONY: clean install

{{progname}}: main.lisp
	./build.sh

clean:
	rm -rf {{progname}}

install: {{progname}}
	test -n "$(BINDIR)"  # $$BINDIR
	cp {{progname}} ${BINDIR}
