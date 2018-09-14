#!/bin/bash

# Cleanup
docker rm -vf `docker ps -qa`
docker image rm -f sb-challenge:jenkins
