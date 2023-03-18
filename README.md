# Setting up Spark cluster in Standalone mode

### Objective: ###
This project helps setting-up spark cluster in standalone mode on macwindows inside docker terminal. Idea is to do a hands-on in setting up spark using docker and perform a join on data present between SQL Server and MongoDB.

[Here](https://towardsdatascience.com/apache-spark-cluster-on-docker-ft-a-juyterlab-interface-418383c95445) is the referenced article used to create the same: 

Please ensure before starting on latest versions of docker. Buildkit property must be set to false

![buildkit property must be set to false](https://imgur.com/a/vvAzR51)

#### Next Steps
We are going to perform a join between MongoDB collection and table in SQL Server. Data from both of the sources will be pulled into Spark cluster locally and then the join will be performed by Spark.

#### Step by Step Process: ####

Spark Cluster setup process has been broken down into two steps
1. Run `build.bat` or `build.sh` depending upon windows/linux
   ```
   build.bat
   ```

#### URL for accessing UI of Spark nodes: ####
1. JupyterLab at [localhost:8888](http://localhost:8888);
2. Spark master at [localhost:8080](http://localhost:8080);
3. Spark worker I at [localhost:8081](http://localhost:8081);
4. Spark worker II at [localhost:8082](http://localhost:8082);

Optionally you can make an entry into `/etc/hosts` file to replace localhost names with corresponding node names


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

### Pyspark notebook ###
Open the notebook JupyterLab at [localhost:8888](http://localhost:8888) and paste the below code to see Jupyter in action
```
from pyspark.sql import SparkSession
spark = SparkSession.\
        builder.\
        appName("pyspark-notebook").\
        master("spark://spark-master:7077").\
        config("spark.executor.memory", "512m").\
        getOrCreate()

import wget
url = "https://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data"
wget.download(url)
data = spark.read.csv("iris.data")
data.show(n=5)
```