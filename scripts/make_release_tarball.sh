#!/usr/bin/env bash

tar cvzf /tmp/oatmeal-release.tgz target/uberjar/oatmeal.jar scripts/brew_install.sh

sha256sum /tmp/oatmeal-release.tgz
