#!/usr/bin/env bash

set -e

semver=$(grep defproject project.clj | cut -d '"' -f 2)
echo "v$semver"

gh release create v$semver --notes "$semver: scripted release"

sha=$(curl https://github.com/eigenhombre/oatmeal/archive/refs/tags/v$semver.tar.gz | sha256sum)

echo "$semver : $sha"
