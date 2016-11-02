#!/bin/bash

if [ $# -ne 1 ]; then
    echo "Specify docker image version to run"
    exit 1
fi

VERSION=$1

docker rm -f hadoop-master &> /dev/null
echo "Start 'hadoop-master' container..."
sudo docker run -itd \
    --net=hadoop \
    -p 50070:50070 \
    -p 8088:8088 \
    -p 22:22 \
    --name hadoop-master \
    --hostname hadoop-master \
    rudkodm/hadoop:${VERSION}

echo -e "root\nroot" | docker exec -i hadoop-master passwd
