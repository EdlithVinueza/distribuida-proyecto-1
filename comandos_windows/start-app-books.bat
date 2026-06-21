@echo off
title App Books Cluster

cd /d "C:\Users\Edlith Vinueza\Documents\UCE 26-26\Distribuida\distribuida-proyecto-1\app-books"

start "BOOK-8081" cmd /k java -Dquarkus.http.port=8081 -jar build\quarkus-app\quarkus-run.jar