#!/usr/bin/env bash
cat artifacts/jira.tar.gz.part* > build/jira.tar.gz
gradle untar :scrum8-service:bootJar
docker-compose build
docker-compose up -d

