#!/bin/bash

VERSION=0.0.1

echo -e "\n Start Hadoop image build v:${VERSION} \n"

sudo docker build --build-arg CONFIGS="config-pseudo" . -t rudkodm/hadoop-single:${VERSION}

