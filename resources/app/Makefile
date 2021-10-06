.PHONY: clean install

{{projname}}: src/*.lisp
	./build.sh

clean:
	rm -rf {{projname}}

install: {{projname}}
	test -n "$(BINDIR)"  # $$BINDIR
	cp {{projname}} ${BINDIR}
