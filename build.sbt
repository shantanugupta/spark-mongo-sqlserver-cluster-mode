name := "sql-mongo-validation"
version := "0.1"
scalaVersion := "2.12.10"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.1.2" % "provided"
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.1.2" % "provided"
// https://mvnrepository.com/artifact/com.microsoft.sqlserver/mssql-jdbc
libraryDependencies += "com.microsoft.sqlserver" % "mssql-jdbc" % "7.4.1.jre11"
// https://mvnrepository.com/artifact/org.mongodb.spark/mongo-spark-connector
libraryDependencies += "org.mongodb.spark" %% "mongo-spark-connector" % "3.0.1"

Compile / mainClass  := Some("com.sparkTutorial.input")
assembly / mainClass := Some("com.sparkTutorial.input")

assembly / assemblyMergeStrategy  := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

/*
docker cp /Users/shangupta/IdeaProjects/sql-mongo-validation/target/scala-2.12/sql-mongo-validation-assembly-0.1.jar master-node:/home/shangupta/
docker cp /Users/shangupta/IdeaProjects/sql-mongo-validation/cluster-properties.txt master-node:/home/shangupta/


spark-submit \
 --class com.sparkTutorial.input \
 --deploy-mode client \
 --master "spark://master-node:7077" \
 target/scala-2.12/sql-mongo-validation-assembly-0.1.jar

 --properties-file /home/shangupta/cluster-properties.txt \
  spark.dynamicAllocation.enabled true
*/


//docker cp out/artifacts/sql_mongo_validation_jar/sql-mongo-validation.jar master-node:/home/shangupta