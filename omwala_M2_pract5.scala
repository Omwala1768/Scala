import scala.io.Source
import breeze.linalg._

object omwala_M2_pract5 {

  def main(args: Array[String]): Unit = {

    println("Om Wala S119")
    println("Linear Regression using Breeze")
    println("--------------------------------")

    val fileName = "/fifa.csv"

    val xColumn = "ARG"
    val yColumn = "ESP"

    try {

      val stream = getClass.getResourceAsStream(fileName)

      if (stream == null) {
        println("CSV file not found in resources folder.")
        return
      }

      val source = Source.fromInputStream(stream)

      val lines = source.getLines().toList

      val headers = lines.head.split(",").map(_.trim)

      val xIndex = headers.indexOf(xColumn)
      val yIndex = headers.indexOf(yColumn)

      if (xIndex == -1 || yIndex == -1) {
        println("Required column not found.")
        return
      }

      val data = lines.tail
        .map(_.split(",").map(_.trim))
        .filter(row =>
          row.length > math.max(xIndex, yIndex) &&
            row(xIndex).toDoubleOption.isDefined &&
            row(yIndex).toDoubleOption.isDefined
        )
        .map(row =>
          (row(xIndex).toDouble, row(yIndex).toDouble)
        )

      val smallData = data.take(10)

      val x = DenseVector(smallData.map(_._1).toArray)
      val y = DenseVector(smallData.map(_._2).toArray)

      val xMean = sum(x) / x.length
      val yMean = sum(y) / y.length

      val numerator = sum((x - xMean) * (y - yMean))
      val denominator = sum((x - xMean) * (x - xMean))

      val slope = numerator / denominator

      val intercept = yMean - slope * xMean

      println()
      println("Model Results")
      println("--------------------------------")
      println(f"Slope     : $slope%.4f")
      println(f"Intercept : $intercept%.4f")

      println()
      println(s"Regression Equation:")
      println(f"$yColumn = $intercept%.4f + $slope%.4f * $xColumn")

      val predictionX = x(0)

      val predictionY = intercept + slope * predictionX

      println()
      println("Prediction")
      println("--------------------------------")
      println(f"$xColumn value : $predictionX%.2f")
      println(f"Predicted $yColumn : $predictionY%.2f")

      source.close()

    } catch {
      case e: Exception =>
        println("Error: " + e.getMessage)
    }
  }
}