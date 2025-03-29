# oatmeal

<img src="/oats.jpg" width="400">

> "Lisp has all the visual appeal of oatmeal with fingernail clippings mixed in."
> -- Larry Wall

![build](https://github.com/eigenhombre/oatmeal/actions/workflows/build.yml/badge.svg)

A small tool for generating Common Lisp projects, including unit tests
and building stand-alone applications.

*N.B.*: I am currently using
[Steelcut](https://github.com/eigenhombre/steelcut), my Common Lisp
implementation of this project, for new Lisp projects.

## Install

On Mac OS,

    brew install eigenhombre/brew/oatmeal

So far Homebrew is the only package manager supported; PRs for others
will be gratefully reviewed!

## Usage

<!-- BEGIN OATMEAL USAGE -->
```

Usage: oatmeal create lib <libname>
       oatmeal create app <appname>
       oatmeal update readme

If the environment variable LISP_HOME is defined, sources will be
created there.  Otherwise, sources will be created in a subdirectory
of the current working directory. If the directory
`./<libname|appname>` already exists, we will not overwrite its
contents.

For "make install" to work correctly, set an environment BINDIR for
executable files to be placed in.

```
<!-- END OATMEAL USAGE -->

### Quicklisp Setup

Oatmeal apps are set up to rely on
[Quicklisp](https://www.quicklisp.org/beta/) for managing
dependencies.  Quicklisp needs some setup before use.  The script
`oatmeal-quicklisp-install` downloads Quicklisp, installs it into a
directory `quicklisp` in your home directory, and adds it to your SBCL
init file.  If you are using a different Common Lisp implementation
(e.g. CCL or Clisp rather than SBCL), or want a different location for
the `quicklisp` directory, you'll have to do that setup manually.

You can run `oatmeal` before `oatmeal-quicklisp-install` (which only
need be run once), but you'll have to run the latter script at least
once before building an Oatmeal-generated app.

### Unit Testing

Upon generating a library or application, an example test can be found
in the file `test/test.lisp`.  The minimalist
[1AM](https://github.com/lmj/1am) test framework is used.  `make test`
in the generated project will run the unit tests.

## Example

### Creating an app

    $ cd /tmp
    $ oatmeal create app cranky
    APP /private/tmp/cranky

### Running unit tests

    $ cd cranky

    $ make test
    ./test.sh
    This is SBCL 2.2.6, an implementation of ANSI Common Lisp.
    More information about SBCL is available at <http://www.sbcl.org/>.

    SBCL is free software, provided as is, with absolutely no warranty.
    It is mostly in the public domain; some portions are provided under
    BSD-style licenses.  See the CREDITS and COPYING files in the
    distribution for more information.
    To load "1am":
      Load 1 ASDF system:
        1am
    ; Loading "1am"

    To load "cranky":
      Load 1 ASDF system:
        cranky
    ; Loading "cranky"
    [package cranky]
    ; compiling file "/Users/jacobsen/Programming/Lisp/common-lisp/cranky/test/package.lisp" (written 02 JUL 2022 08:26:35 AM):

    ; wrote /Users/jacobsen/.cache/common-lisp/sbcl-2.2.6-macosx-arm64/Users/jacobsen/Programming/Lisp/common-lisp/cranky/test/package-tmp5GEXGEG5.fasl
    ; compilation finished in 0:00:00.001
    ; compiling file "/Users/jacobsen/Programming/Lisp/common-lisp/cranky/test/test.lisp" (written 02 JUL 2022 08:26:35 AM):

    ; wrote /Users/jacobsen/.cache/common-lisp/sbcl-2.2.6-macosx-arm64/Users/jacobsen/Programming/Lisp/common-lisp/cranky/test/test-tmpAR3FSGEY.fasl
    ; compilation finished in 0:00:00.001
    CRANKY.TEST::EXAMPLE.
    Success: 1 test, 1 check.

### Building the executable

    $ make
    ./build.sh
    This is SBCL 2.2.6, an implementation of ANSI Common Lisp.
    More information about SBCL is available at <http://www.sbcl.org/>.

    SBCL is free software, provided as is, with absolutely no warranty.
    It is mostly in the public domain; some portions are provided under
    BSD-style licenses.  See the CREDITS and COPYING files in the
    distribution for more information.
    To load "cranky":
      Load 1 ASDF system:
        cranky
    ; Loading "cranky"

    [undoing binding stack and other enclosing state... done]
    [performing final GC... done]
    [saving current Lisp image into cranky:
    writing 1840 bytes from the read-only space at 0x300000000
    writing 1840 bytes from the static space at 0x300200000
    writing 0 bytes from the immobile space at 0x300300000
    writing 42139648 bytes from the dynamic space at 0x7003000000
    done]

### Running the executable

    $ ./cranky
    Thanks for using cranky!
    $

## Building Oatmeal Locally

(On MacOS; your mileage may vary on other platforms.) First install
Java and [Leiningen](https://leiningen.org/).  Then `brew install sbcl`.

    $ make
    $ make install

    $ java -jar ~/bin/oatmeal.jar

To update this file (`README.md`), simply:

    make doc

## License

Copyright © 2021-2022 John Jacobsen

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT
OF THIRD PARTY RIGHTS. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
