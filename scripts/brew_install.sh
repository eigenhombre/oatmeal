#!/usr/bin/env bash

prefix="$1"

mkdir -p "$prefix/bin"

cat <<EOF > oatmeal
#!/usr/bin/env bash
echo "I am roaring, hear me make oatmeal."
EOF

chmod 0755 oatmeal
cp oatmeal "$prefix/bin"


