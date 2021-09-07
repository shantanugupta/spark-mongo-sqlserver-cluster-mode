# Setting up Spark cluster in Standalone mode

### Objective: ###
This project helps setting-up spark cluster in standalone mode on mac inside docker terminal. Idea is to do a hands-on in setting up spark using docker and perform a join on data present between SQL Server and MongoDB.


We are going to perform a join between MongoDB collection and table in SQL Server. Data from both of the sources will be pulled into Spark cluster locally and then the join will be performed by Spark.

#### Step by Step Process: ####

Spark Cluster setup process has been broken down into two steps
1. Install all spark, python and other required binaries. This step creates a docker image with name: `apache-spark` 
    ```
   docker build -t apache-spark ./
   ```
2. Docker-Compose uses image name `apache-spark` for building another docker image capable of starting spark in cluster mode. This step is going to create a docker image and start the cluster
    ```
   docker-compose -p spark up --build
   ```

This newly built image will be named `image: cluster-apache-spark:3.1.2` as it's used in docker-compose.yml

Once we are all set for spark cluster, we can work on scala projects to submit jobs.

####URL for accessing UI of Spark nodes:####
1. Master node:`http://localhost:8000`
2. Worker-Node-1: `http://localhost:8001`
3. Worker-Node-2: `http://localhost:8002`
4. Worker-Node-3: `http://localhost:8003`

Optionally you can make an entry into `/etc/hosts` file to replace localhost names with corresponding node names

Here is an example of my host file
```shell
127.0.0.1	master-node
127.0.0.1	worker-node-1 
127.0.0.1	worker-node-2
127.0.0.1	worker-node-3
```
Same URL can be accessed using node names used in [docker-compose.yml](/docker-compose.yml) via [.env](/.env) file:

1. Master node: [`http://master-node:8000`](`http://master-node:8000`)
2. Worker-Node-1: [`http://worker-node-1:8001`](`http://worker-node-1:8001`)
3. Worker-Node-2: [`http://worker-node-2:8002`](`http://worker-node-2:8002`)
4. Worker-Node-3: [`http://worker-node-3:8003`](`http://worker-node-3:8003`)

To submit the job, login to docker master node using below command on terminal. This will take you into the docker shell.
```
docker exec -it master-node /bin/bash
```

Job can be submitted in master-node using below code. We are submitting the job in client mode.
```
spark-submit \
 --class com.sparkTutorial.input \
 --deploy-mode client \
 --master "spark://master-node:7077" \
 target/scala-2.12/sql-mongo-validation-assembly-0.1.jar
 ```
