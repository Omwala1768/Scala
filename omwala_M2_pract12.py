from pyspark.sql import SparkSession

print("Om Wala S119")
spark = SparkSession.builder \
    .appName("FIFA 2026 CSV Join") \
    .master("local[*]") \
    .getOrCreate()

teams_df = spark.read.csv(
    "fifa_2026_teams.csv",
    header=True,
    inferSchema=True
)

stats_df = spark.read.csv(
    "fifa_2026_stats.csv",
    header=True,
    inferSchema=True
)

print("\nFIFA 2026 Teams:")
teams_df.show()

print("\nFIFA 2026 Stats:")
stats_df.show()

joined_df = teams_df.join(
    stats_df,
    on="team_code",
    how="inner"
)

print("\nJoined FIFA 2026 Data:")
joined_df.show()

joined_df.write \
    .mode("overwrite") \
    .option("header", True) \
    .csv("fifa_2026_joined_output")

print("\nJoined data successfully saved!")

spark.stop()