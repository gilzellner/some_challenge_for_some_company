#!/bin/bash

# Setup
docker build --no-cache -t sb-challenge:jenkins .
docker run -d -p 8080:8080 -p 50000:50000 --name jenkins sb-challenge:jenkins
