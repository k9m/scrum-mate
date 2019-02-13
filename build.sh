#!/usr/bin/env bash
top=$(git rev-parse --show-toplevel)
cd $top

rm -R build 2> /dev/null
mkdir build

cat artifacts/jira.tar.gz.part* > build/jira.tar.gz
gradle untar build
docker-compose up -d

