from pyspark.sql import SparkSession
from pyspark.sql.functions import when
from pyspark.ml import Pipeline
from pyspark.ml.feature import VectorAssembler
from pyspark.ml.classification import LogisticRegression
from pyspark.ml.evaluation import MulticlassClassificationEvaluator

spark = SparkSession.builder \
    .appName("FIFA Classification") \
    .master("local[*]") \
    .getOrCreate()

data = spark.read \
    .option("header", "true") \
    .option("inferSchema", "true") \
    .csv("fifa.csv")

print("FIFA Data:")
data.show()

data = data.withColumn(
    "label",
    when(data["ARG"] <= 10, 1.0).otherwise(0.0)
)

print("Data with Label:")
data.show()

assembler = VectorAssembler(
    inputCols=["BRA", "ESP", "FRA", "GER", "ITA"],
    outputCol="features"
)

logistic_regression = LogisticRegression(
    labelCol="label",
    featuresCol="features",
    maxIter=10
)

pipeline = Pipeline(
    stages=[assembler, logistic_regression]
)

training_data, test_data = data.randomSplit(
    [0.8, 0.2],
    seed=42
)

print("Training Data:")
training_data.show()

print("Test Data:")
test_data.show()

model = pipeline.fit(training_data)

predictions = model.transform(test_data)

print("Predictions:")
predictions.select(
    "ARG",
    "BRA",
    "ESP",
    "FRA",
    "GER",
    "ITA",
    "label",
    "prediction"
).show()

evaluator = MulticlassClassificationEvaluator(
    labelCol="label",
    predictionCol="prediction",
    metricName="accuracy"
)

accuracy = evaluator.evaluate(predictions)

print("Classification Accuracy =", accuracy)

spark.stop()