FROM cluster-base

# -- Layer: Apache Spark
ARG spark_version=3.1.2
ARG hadoop_version=3.2

# Download and uncompress spark from the apache archive
RUN apt-get update -y && \
    apt-get install -y curl && \
    curl https://archive.apache.org/dist/spark/spark-${spark_version}/spark-${spark_version}-bin-hadoop${hadoop_version}.tgz -o spark.tgz && \
    tar -xf spark.tgz && \
    mv spark-${spark_version}-bin-hadoop${hadoop_version} /usr/bin/ && \
    mkdir /usr/bin/spark-${spark_version}-bin-hadoop${hadoop_version}/logs && \
    rm spark.tgz

#SPARK_HOME=/opt/spark 
#PYTHONHASHSEED=1
#COPY ./sqljdbc42.jar /usr/share/java/
# Apache spark environment
ENV SPARK_HOME=/usr/bin/spark-${spark_version}-bin-hadoop${hadoop_version} \
    SPARK_MASTER_HOST=spark-master \
    SPARK_MASTER_PORT=7077 \
    PYSPARK_PYTHON=python3
    # SPARK_LOG_DIR=${SPARK_HOME}/logs \
    # SPARK_MASTER_LOG=${SPARK_HOME}/logs/master.out \
    # SPARK_WORKER_LOG=${SPARK_HOME}/logs/worker.out \
    # SPARK_BIN="${SPARK_HOME}/bin/" \
    # PATH="${PATH}:${SPARK_HOME}/bin/"

WORKDIR ${SPARK_HOME}

# RUN mkdir -p $SPARK_LOG_DIR && \
# touch $SPARK_MASTER_LOG && \
# touch $SPARK_WORKER_LOG && \
# ln -sf /dev/stdout $SPARK_MASTER_LOG && \
# ln -sf /dev/stdout $SPARK_WORKER_LOG

# COPY start-spark.sh /

# CMD ["/bin/bash", "/start-spark.sh"]