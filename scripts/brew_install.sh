#!/usr/bin/env bash

prefix="$1"

mkdir -p "$prefix/bin"

lein uberjar
cp target/uberjar/oatmeal.jar "$prefix/bin"

cat <<EOF > oatmeal
#!/usr/bin/env bash
java -jar "$prefix/bin/oatmeal.jar" $@
EOF

chmod 0755 oatmeal
cp oatmeal "$prefix/bin"


