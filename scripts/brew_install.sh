#!/usr/bin/env bash

prefix="$1"
scriptdir=$(dirname "$0")

mkdir -p "$prefix/bin"

lein uberjar
cp target/uberjar/oatmeal.jar "$prefix/bin"

cat <<EOF > oatmeal
#!/usr/bin/env bash
java -jar "$prefix/bin/oatmeal.jar" "\$@"
EOF

chmod 0755 oatmeal
cp oatmeal "$prefix/bin"
cp $scriptdir/quicklisp-install.sh "$prefix/bin/oatmeal-quicklisp-install"
