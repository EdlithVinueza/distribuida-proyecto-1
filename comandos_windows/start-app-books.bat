@echo off
title App Books Cluster

cd /d "C:\Users\edlit\OneDrive\Documentos\2026-2026\Programacion Ditribuida\distribuida-proyecto-1\app-books"

start "BOOK-8081" cmd /k java -Dquarkus.http.port=8081 -jar build\quarkus-app\quarkus-run.jar