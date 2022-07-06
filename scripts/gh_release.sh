#!/usr/bin/env bash

set -e

semver=$(grep defproject project.clj | cut -d '"' -f 2)
echo "v$semver"

gh release create v$semver --notes "$semver: scripted release"

#sha=$(curl https://github.com/eigenhombre/oatmeal/archive/refs/tags/v$semver.tar.gz | sha256sum)
curl https://github.com/eigenhombre/oatmeal/archive/refs/tags/v$semver.tar.gz > /tmp/x
sha=$(sha256sum /tmp/x)
echo "$semver : $sha"
