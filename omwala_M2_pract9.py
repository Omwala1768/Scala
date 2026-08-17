import os

os.environ["PYSPARK_PYTHON"] = r"D:\Om Wala SYCS\Scala\PySparkWordCount\.venv\Scripts\python.exe"
os.environ["PYSPARK_DRIVER_PYTHON"] = r"D:\Om Wala SYCS\Scala\PySparkWordCount\.venv\Scripts\python.exe"

from pyspark.sql import SparkSession

spark = SparkSession.builder \
    .appName("WordCount") \
    .master("local[*]") \
    .getOrCreate()

text_file = spark.sparkContext.textFile("input.txt")

word_counts = (
    text_file
    .flatMap(lambda line: line.split())
    .map(lambda word: (word, 1))
    .reduceByKey(lambda a, b: a + b)
)

for word, count in word_counts.collect():
    print(word, ":", count)

spark.stop()