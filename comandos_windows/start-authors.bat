@echo off
title App Authors Cluster

cd /d "C:\Users\Edlith Vinueza\Documents\UCE 26-26\Distribuida\distribuida-proyecto-1\app-authors"

start "AUTH-8070" cmd /k java -Dquarkus.http.port=8070 -jar build/quarkus-app/quarkus-run.jar

start "AUTH-8071" cmd /k java -Dquarkus.http.port=8071 -jar build/quarkus-app/quarkus-run.jar