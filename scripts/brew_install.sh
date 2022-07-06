#!/usr/bin/env bash

prefix="$1"

mkdir -p "$prefix/bin"

lein uberjar

cat <<EOF > oatmeal
#!/usr/bin/env bash
echo "I am roaring, hear me make oatmeal."
EOF

chmod 0755 oatmeal
cp oatmeal "$prefix/bin"


