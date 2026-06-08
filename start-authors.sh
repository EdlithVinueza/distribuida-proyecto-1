#!/bin/bash
cd "/home/edlith/Documentos/UCE 2026-2026/Proyectos Programacion Distriuida/distribuida-proyecto-1/app-authors"

# Iniciamos la primera instancia en segundo plano (con el &)
java -Dquarkus.http.port=8070 -jar build/quarkus-app/quarkus-run.jar &
PID1=$!

# Iniciamos la segunda instancia en primer plano
java -Dquarkus.http.port=8071 -jar build/quarkus-app/quarkus-run.jar


