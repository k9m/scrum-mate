#!/usr/bin/env bash
top=$(git rev-parse --show-toplevel)
cd $top

rm -R build 2> /dev/null
mkdir build

cat artifacts/jira.tar.gz.part* > build/jira.tar.gz
gradle clean untar build

cd scrum8-ocr
docker build . -t fca/scrum8-ocr:latest

cd $top
cd scrum8-services
docker build . -t fca/scrum8-services:latest

docker-compose build
docker-compose up -d

