#!/usr/bin/env bash

prefix="$1"

mkdir -p "$prefix/bin"

cat <<EOF > oatmeal
#!/usr/bin/env bash
echo "I am oatmeal, hear me roar."
EOF

chmod 0755 oatmeal
cp oatmeal "$prefix/bin"


