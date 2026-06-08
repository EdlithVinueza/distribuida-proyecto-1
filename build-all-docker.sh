#!/bin/bash

# Construimos app-authors
cd app-authors
docker build -f src/main/docker/Dockerfile.jvm -t edlith777/app-authors .
cd ..

# Construimos app-books
cd app-books
docker build -f src/main/docker/Dockerfile.jvm -t edlith777/app-books .
cd ..

# Construimos app-customers
cd app-customers
docker build -f src/main/docker/Dockerfile.jvm -t edlith777/app-customers .
cd ..
