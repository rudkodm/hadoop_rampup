FROM ubuntu:14.04
MAINTAINER rudkodm <rudko.d.v@gmail.com>

# Args:
ARG CONFIGS

WORKDIR /root

# install openssh-server, openjdk and wget
RUN apt-get update && \
    apt-get install -y software-properties-common python-software-properties && \
    add-apt-repository -y ppa:openjdk-r/ppa && \
    apt-get update && \
    apt-get install -y openssh-server openjdk-8-jdk wget

# install hadoop 2.7.3
RUN wget http://www.eu.apache.org/dist/hadoop/common/hadoop-2.7.3/hadoop-2.7.3.tar.gz && \
    tar -xzvf hadoop-2.7.3.tar.gz && \
    mv hadoop-2.7.3 /usr/local/hadoop && \
    rm hadoop-2.7.3.tar.gz

RUN cd /usr/local/hadoop

# set environment variable
ENV JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
ENV HADOOP_HOME=/usr/local/hadoop
ENV PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin

# ssh without key
RUN ssh-keygen -t rsa -f ~/.ssh/id_rsa -P '' && \
    cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys


COPY ${CONFIGS}/* /tmp/

RUN mv /tmp/ssh_config          ~/.ssh/config && \
    mv /tmp/sshd_config         /etc/ssh && \
    mv /tmp/hadoop-env.sh       $HADOOP_HOME/etc/hadoop/hadoop-env.sh && \
    mv /tmp/hdfs-site.xml       $HADOOP_HOME/etc/hadoop/hdfs-site.xml && \
    mv /tmp/core-site.xml       $HADOOP_HOME/etc/hadoop/core-site.xml && \
    mv /tmp/mapred-site.xml     $HADOOP_HOME/etc/hadoop/mapred-site.xml && \
    mv /tmp/yarn-site.xml       $HADOOP_HOME/etc/hadoop/yarn-site.xml && \
    mv /tmp/slaves              $HADOOP_HOME/etc/hadoop/slaves && \
    mv /tmp/start-hadoop.sh     ~/start-hadoop.sh && \
    mv /tmp/run-wordcount.sh    ~/run-wordcount.sh

RUN chmod +x ~/start-hadoop.sh && \
    chmod +x ~/run-wordcount.sh && \
    chmod +x $HADOOP_HOME/sbin/start-dfs.sh && \
    chmod +x $HADOOP_HOME/sbin/start-yarn.sh

# format namenode
RUN $HADOOP_HOME/bin/hdfs namenode -format

CMD [ "sh", "-c", "service ssh start; bash"]