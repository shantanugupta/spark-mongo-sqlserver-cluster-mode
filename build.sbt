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

Compile / mainClass  := Some("input")
assembly / mainClass := Some("input")

assembly / assemblyMergeStrategy  := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

/*
spark-submit \
 --deploy-mode cluster \
 --master "spark://localhost:7077" \
 /Users/shangupta/IdeaProjects/sql-mongo-validation/target/scala-2.12/sql-mongo-validation-assembly-0.1.jar
*/