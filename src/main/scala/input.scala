import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrameReader, SparkSession}

object input {

  var tuidIn = Seq(50796445, 50728816) //, 50728826, 50728821, 50728820, 50728817, 50728815, 50728813, 50728812, 50728811, 50728810, 50728809, 50728806, 50728804, 50728800, 50728798, 50728793, 50728791, 50728790, 50728789, 50728788, 50728787, 50728786, 50728782, 50728781, 50728780, 50728777, 50728776, 50728775, 50728773, 50728772, 50728770, 50728769, 50728765, 50728761, 50728759, 50728758, 50728756, 50728755, 50728754, 50728753, 50728752, 50728751, 50728750, 50728744, 50728743, 50728741, 50728740, 50728739, 50728737, 50728732, 50728731, 50728730, 50728728, 50728727, 50728726, 50728725, 50728723, 50728721, 50728719, 50728716, 50728715, 50728714, 50728713, 50728712, 50728711, 50728709, 50728708, 50728705, 50728704, 50728703, 50728699, 50728698, 50728697, 50728696, 50728695, 50728694, 50728693, 50728688, 50728686, 50728684, 50728683, 50728681, 50728680, 50728676, 50728674, 50728673, 50728672, 50728669, 50728666, 50728665, 50728663, 50728661, 50728660, 50728659, 50728658, 50728657, 50728654, 50728653)

  def main(args: Array[String]): Unit = {
//  val sparkServer = "local"
    val sparkServer = "spark://localhost:7077"
    val conf = new SparkConf().setMaster(sparkServer).setAppName("FirstDemo")
    val sc = new SparkContext(conf)

    val spark = SparkSession.builder().config(sc.getConf).
      appName("Spark SQL basic example").
      config("spark.some.config.option", "some-value").
      getOrCreate()

    val jdbcReader = sqlServerReader(spark)
    createDataFrames(spark, jdbcReader)
    getData(spark)

    //testSparkCluster(sc)
    //joinExample(spark)
  }

  def joinExample(spark: SparkSession):Unit={
    val emp = Seq((1,"Smith",-1,"2018","10","M",3000),
      (2,"Rose",1,"2010","20","M",4000),
      (3,"Williams",1,"2010","10","M",1000),
      (4,"Jones",2,"2005","10","F",2000),
      (5,"Brown",2,"2010","40","",-1),
      (6,"Brown",2,"2010","50","",-1)
    )
    val empColumns = Seq("emp_id","name","superior_emp_id","year_joined",
      "emp_dept_id","gender","salary")
    import spark.sqlContext.implicits._
    val empDF = emp.toDF(empColumns:_*)
    empDF.show(false)

    val dept = Seq(("Finance",10),
      ("Marketing",20),
      ("Sales",30),
      ("IT",40)
    )

    val deptColumns = Seq("dept_name","dept_id")
    val deptDF = dept.toDF(deptColumns:_*)
    deptDF.show(false)

    empDF.join(deptDF,empDF("emp_dept_id") ===  deptDF("dept_id"),"inner")
      .show(false)
  }

  def testSparkCluster(sc: SparkContext): Unit = {
    val rdd = sc.parallelize(Array(1, 2, 3))
    println("hello ooooooooooo --- " + rdd.reduce(_ + _))
  }

  def sqlServerReader(spark: SparkSession): DataFrameReader = {
    var server: String = SQLServerCredentials.getServerName()
    val database = SQLServerCredentials.database
    val user = SQLServerCredentials.user
    val password = SQLServerCredentials.password

    //returns JDBC reader
    spark.read.format("jdbc").
      option("url", f"jdbc:sqlserver://$server:1433;databaseName=$database;").
      option("user", user).
      option("password", password).
      option("driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
  }

  def createDataFrames(spark: SparkSession,
                       jdbcReader: DataFrameReader): Unit = {
    var queries = Queries.getQueries(tuidIn)

    //createOrReplaceTempView for SQL Server and MongoDB data
    queries.map({ case (view_name, sourceType, query) =>
      println(s"---- Getting schema for $view_name from $sourceType ----")

      val df: org.apache.spark.sql.DataFrame = if (sourceType == "sqlserver") {
        jdbcReader.option("query", query).load()
      } else if (sourceType == "mongodb") {
        var connectionString = MongoDBCredentials.getConnectionString(view_name)
        spark.read.format("com.mongodb.spark.sql.DefaultSource").
          option("spark.mongodb.input.partitioner", "MongoSinglePartitioner").
          option("uri", connectionString).
          load()
      }
      else {
        spark.emptyDataFrame
      }

      df.createOrReplaceTempView(view_name)
      (view_name, df) //this is how return works in scala
    })
  }

  def getData(spark: SparkSession): Unit = {
    var transformationQueries = TransformationQueries.getTransformationQueries(tuidIn)
    transformationQueries.map({ case (name, transformation) =>
      println(s"Running transformation $name - Calling action SHOW")
      if (name != "profile_transformation")
        spark.sql(transformation).show()
    })
  }
}