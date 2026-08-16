import breeze.linalg._
import scala.io.Source
import scala.util.Random

object omwala_M2_pract8 {

  def main(args: Array[String]): Unit = {

    println("Om Wala S119")
    println("K-Means Clustering using Player Attributes")
    println("-------------------------------------------")

    val filePath = "src/main/resources/Player_Attributes.csv"

    val source = Source.fromFile(filePath)
    val lines = source.getLines().toList
    source.close()

    val headers = lines.head.split(",").map(_.trim)

    val featureColumns = Array(
      "overall_rating",
      "potential",
      "crossing",
      "finishing",
      "dribbling"
    )

    val featureIndexes =
      featureColumns.map(column => headers.indexOf(column))

    if (featureIndexes.contains(-1)) {
      println("Required columns were not found.")
      return
    }

    val dataRows = lines.tail.flatMap { line =>

      val values = line.split(",", -1).map(_.trim)

      try {
        Some(
          featureIndexes.map(index => values(index).toDouble)
        )
      } catch {
        case _: Exception => None
      }
    }

    val maxSamples = 5000

    val selectedRows =
      Random.shuffle(dataRows).take(maxSamples)

    val numSamples = selectedRows.length
    val numFeatures = featureColumns.length

    val data =
      DenseMatrix.zeros[Double](numSamples, numFeatures)

    for (i <- 0 until numSamples) {
      for (j <- 0 until numFeatures) {
        data(i, j) = selectedRows(i)(j)
      }
    }

    println(
      s"Dataset loaded with $numSamples samples and $numFeatures features."
    )

    println("\nFeatures used:")

    featureColumns.foreach { column =>
      println(column)
    }

    print("\nEnter number of clusters (K): ")
    val k = scala.io.StdIn.readInt()

    if (k <= 0 || k > numSamples) {
      println("Invalid value of K.")
      return
    }

    val maxIterations = 100

    var centroids =
      DenseMatrix.zeros[Double](k, numFeatures)

    val randomIndices =
      Random.shuffle((0 until numSamples).toList).take(k)

    for (i <- 0 until k) {
      for (j <- 0 until numFeatures) {
        centroids(i, j) =
          data(randomIndices(i), j)
      }
    }

    println("\nInitial Centroids:")
    println(centroids)

    var assignments =
      DenseVector.zeros[Int](numSamples)

    var previousAssignments =
      DenseVector.fill(numSamples)(-1)

    var iteration = 0
    var converged = false

    while (iteration < maxIterations && !converged) {

      for (i <- 0 until numSamples) {

        val point =
          DenseVector(
            (0 until numFeatures)
              .map(j => data(i, j))
              .toArray
          )

        var minDistance =
          Double.MaxValue

        var closestCentroid =
          -1

        for (j <- 0 until k) {

          val centroid =
            DenseVector(
              (0 until numFeatures)
                .map(column => centroids(j, column))
                .toArray
            )

          val distance =
            euclideanDistance(point, centroid)

          if (distance < minDistance) {
            minDistance = distance
            closestCentroid = j
          }
        }

        assignments(i) =
          closestCentroid
      }

      converged =
        assignments == previousAssignments

      previousAssignments =
        assignments.copy

      val newCentroids =
        DenseMatrix.zeros[Double](k, numFeatures)

      val clusterCounts =
        DenseVector.zeros[Int](k)

      for (i <- 0 until numSamples) {

        val clusterId =
          assignments(i)

        for (j <- 0 until numFeatures) {

          newCentroids(clusterId, j) +=
            data(i, j)
        }

        clusterCounts(clusterId) += 1
      }

      for (i <- 0 until k) {

        if (clusterCounts(i) > 0) {

          for (j <- 0 until numFeatures) {

            newCentroids(i, j) =
              newCentroids(i, j) /
                clusterCounts(i).toDouble
          }
        }
      }

      centroids =
        newCentroids

      iteration += 1

      println(
        s"Iteration $iteration completed."
      )
    }

    println("\n--- Final Results ---")

    println(
      s"K-Means converged in $iteration iterations."
    )

    println("\nFinal Centroids:")

    for (i <- 0 until k) {

      print(s"Cluster ${i + 1}: ")

      for (j <- 0 until numFeatures) {

        print(
          f"${centroids(i, j)}%.2f "
        )
      }

      println()
    }

    println("\nCluster Sizes:")

    for (i <- 0 until k) {

      val count =
        (0 until numSamples)
          .count(index => assignments(index) == i)

      println(
        s"Cluster ${i + 1}: $count players"
      )
    }

    println("\nFirst 20 Cluster Assignments:")

    for (i <- 0 until math.min(20, numSamples)) {

      println(
        s"Player ${i + 1} -> Cluster ${assignments(i) + 1}"
      )
    }
  }
}