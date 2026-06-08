#!/bin/bash
cd "/home/edlith/Documentos/UCE 2026-2026/Proyectos Programacion Distriuida/distribuida-proyecto-1/app-books"

java -Dquarkus.http.port=8081 -jar build/quarkus-app/quarkus-run.jar
