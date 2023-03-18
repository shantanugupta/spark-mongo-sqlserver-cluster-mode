# Setting up Spark cluster in Standalone mode

### Objective: ###
This project helps setting-up spark cluster in standalone mode on mac or windows inside docker terminal. Idea is to have a playground for learning Spark using pyspark or other Interpretors that can be setup on this image.

[Here](https://towardsdatascience.com/apache-spark-cluster-on-docker-ft-a-juyterlab-interface-418383c95445) is the referenced article used to create the functional version of the same docker: 

Please ensure to set below shown property in docker desktop before starting wtih running commands. Buildkit property must be set to false

![buildkit property must be set to false](https://i.imgur.com/Vvbuoqw.png)

#### Step by Step Process: ####

Clone the repo.

1. Run [build.bat](build.bat) or [build.sh](build.sh) depending upon windows or linux on which you are running the command.

   ```
   build.bat 
   ```
   Depending upon size of images, speed of your connection, it may take some time to download all the images for the first time.

2. Run `docker compose up` command to start the container once step 1 is complete

Once above steps are done, you can access spark cluster using following links

#### URL for accessing UI of Spark nodes: ####
1. JupyterLab at [localhost:8888](http://localhost:8888);
2. Spark master at [localhost:8080](http://localhost:8080);
3. Spark worker I at [localhost:8081](http://localhost:8081);
4. Spark worker II at [localhost:8082](http://localhost:8082);

Optionally you can make an entry into `/etc/hosts` file to replace localhost names with corresponding node names

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

### Areas to explore

1. [Working with delta lake](https://towardsdatascience.com/hands-on-introduction-to-delta-lake-with-py-spark-b39460a4b1ae)

2. Playing around with - Job can be submitted in master-node using below code. We are submitting the job in client mode.
```
spark-submit \
 --class com.sparkTutorial.input \
 --deploy-mode client \
 --master "spark://master-node:7077" \
 target/scala-2.12/sql-mongo-validation-assembly-0.1.jar
 ```