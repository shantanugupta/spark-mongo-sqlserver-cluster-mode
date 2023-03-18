@echo off

SET SPARK_VERSION=3.1.2
SET HADOOP_VERSION=3.2
SET JUPYTERLAB_VERSION=2.1.5


docker build -f dockfiles/cluster-base.Dockerfile -t cluster-base .
docker build --build-arg spark_version=%SPARK_VERSION% --build-arg jupyterlab_version=%JUPYTERLAB_VERSION% -f dockfiles/jupyterlab.Dockerfile -t jupyterlab .
docker build --build-arg spark_version=%SPARK_VERSION% --build-arg hadoop_version=%HADOOP_VERSION% -f dockfiles/spark-base.Dockerfile -t spark-base .
docker build -f dockfiles/\spark-master.Dockerfile -t spark-master .
docker build -f dockfiles/\spark-worker.Dockerfile -t spark-worker .
