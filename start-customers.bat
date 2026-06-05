@echo off
title App Customers

cd /d "C:\Users\edlit\OneDrive\Documentos\2026-2026\Programacion Ditribuida\distribuida-proyecto-1\app-customers"

start "CUSTOMERS-8090" cmd /k java -jar build\libs\app-customers-1.0-SNAPSHOT.jar --server.port=8090