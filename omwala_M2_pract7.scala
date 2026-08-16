import breeze.linalg.{DenseVector, euclideanDistance}
import scala.io.Source

object omwala_M2_pract7 {

  case class DataPoint(
                        features: DenseVector[Double],
                        label: String,
                        playerId: String
                      )

  def main(args: Array[String]): Unit = {

    println("Om Wala S119")
    println("K-Nearest Neighbor Classification")
    println("----------------------------------")


    val filePath = "src/main/resources/Player_Attributes.csv"


    val source = Source.fromFile(filePath)
    val lines = source.getLines().toList
    source.close()


    val headers = lines.head.split(",").map(_.trim)


    val featureColumns = Array(
      "potential",
      "crossing",
      "finishing",
      "dribbling",
      "short_passing"
    )


    val featureIndexes =
      featureColumns.map(column => headers.indexOf(column))

    val overallIndex =
      headers.indexOf("overall_rating")

    val idIndex =
      headers.indexOf("id")


    if (featureIndexes.contains(-1)) {
      println("Error: Feature column not found.")
      return
    }

    if (overallIndex == -1) {
      println("Error: overall_rating column not found.")
      return
    }

    if (idIndex == -1) {
      println("Error: id column not found.")
      return
    }


    val dataset = lines.tail.flatMap { line =>

      val values = line.split(",", -1).map(_.trim)

      try {

        val features = DenseVector(
          featureIndexes.map(index => values(index).toDouble): _*
        )

        val overallRating =
          values(overallIndex).toDouble

        val playerId =
          values(idIndex)


        val label =
          if (overallRating < 60) {
            "Low"
          } else if (overallRating < 75) {
            "Medium"
          } else {
            "High"
          }

        Some(
          DataPoint(
            features,
            label,
            playerId
          )
        )

      } catch {
        case _: Exception => None
      }
    }

    println(
      s"\nTotal records loaded: ${dataset.length}"
    )

    println("\nFeatures used:")
    featureColumns.foreach(column =>
      println(s"- $column")
    )

    println("\nClass:")
    println("Low    : Overall Rating < 60")
    println("Medium : Overall Rating 60-74")
    println("High   : Overall Rating >= 75")

    print("\nEnter K value: ")
    val k = scala.io.StdIn.readInt()

    if (k <= 0 || k >= dataset.length) {
      println("Invalid K value.")
      return
    }

    print("Enter Player ID to classify: ")
    val inputId = scala.io.StdIn.readLine()

    // Find selected player
    val selectedPlayer =
      dataset.find(_.playerId == inputId)

    if (selectedPlayer.isEmpty) {
      println("Player ID not found.")
      return
    }

    val player = selectedPlayer.get

    println("\nSelected Player")
    println("----------------------------------")
    println(s"Player ID: ${player.playerId}")
    println(s"Features: ${player.features}")
    println(s"Actual Class: ${player.label}")


    val distances =
      dataset
        .filter(_.playerId != inputId)
        .map { point =>

          val distance =
            euclideanDistance(
              player.features,
              point.features
            )

          (distance, point.label)
        }


    val nearestNeighbors =
      distances
        .sortBy(_._1)
        .take(k)


    println("\nNearest Neighbors")
    println("----------------------------------")

    nearestNeighbors.zipWithIndex.foreach {
      case ((distance, label), index) =>

        println(
          s"${index + 1}. " +
            f"Distance = $distance%.4f" +
            s", Class = $label"
        )
    }


    val votes =
      nearestNeighbors
        .groupBy(_._2)
        .map {
          case (label, records) =>
            (label, records.size)
        }


    val predictedLabel =
      votes.maxBy(_._2)._1


    println("\nVoting Result")
    println("----------------------------------")

    votes.foreach {
      case (label, count) =>
        println(
          s"$label = $count vote(s)"
        )
    }

    println("\nClassification Result")
    println("----------------------------------")

    println(s"K Value: $k")
    println(s"Actual Class: ${player.label}")
    println(s"Predicted Class: $predictedLabel")

    if (player.label == predictedLabel) {
      println("Prediction: Correct")
    } else {
      println("Prediction: Incorrect")
    }
  }
}