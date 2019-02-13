#!/usr/bin/env bash
gradle clean untar :scrum8-service:bootJar
docker-compose build
docker-compose up -d

