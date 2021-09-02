name := "sql-mongo-validation"
version := "0.1"
scalaVersion := "2.12.10"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.1.2"
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.1.2"
// https://mvnrepository.com/artifact/com.microsoft.sqlserver/mssql-jdbc
libraryDependencies += "com.microsoft.sqlserver" % "mssql-jdbc" % "7.4.1.jre11"
// https://mvnrepository.com/artifact/org.mongodb.spark/mongo-spark-connector
libraryDependencies += "org.mongodb.spark" %% "mongo-spark-connector" % "3.0.1"

libraryDependencies += "org.mongodb" % "mongodb-driver" % "3.0.1" // from "https://repo1.maven.org/maven2/org/mongodb/mongodb-driver/mongodb-driver-3.0.1.jar"
libraryDependencies += "org.mongodb" % "mongodb-driver-core" % "3.8.1" // from "https://repo1.maven.org/maven2/org/mongodb/mongodb-driver-core/3.8.1/mongodb-driver-core-3.8.1.jar"
libraryDependencies += "org.mongodb" % "bson" % "3.8.1" //from "https://repo1.maven.org/maven2/org/mongodb/bson/3.8.1/bson-3.8.1.jar"

Compile / mainClass  := Some("input")
assembly / mainClass := Some("input")

assembly / assemblyMergeStrategy  := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

/*
spark-submit \
 --deploy-mode cluster \
 --master "spark://master-node:7077" \
 --class input \
 --packages org.mongodb.spark:mongo-spark-connector_2.12:3.0.1 \
  /home/jars/sql-mongo-validation.jar
*/

//docker cp out/artifacts/sql_mongo_validation_jar/sql-mongo-validation.jar master-node:/home/shangupta