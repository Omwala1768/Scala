import scala.io.Source
import breeze.linalg._

object omwala_M2_pract6 {

  def sigmoid(z: Double): Double = {
    1.0 / (1.0 + math.exp(-z))
  }

  def main(args: Array[String]): Unit = {

    println("Om Wala S119")
    println("Logistic Regression using Breeze")
    println("--------------------------------")

    val fileName = "/fifa.csv"


    val featureColumn = "ARG"
    val targetColumn = "ESP"

    try {

      val stream = getClass.getResourceAsStream(fileName)

      if (stream == null) {
        println("CSV file not found in resources folder.")
        return
      }

      val source = Source.fromInputStream(stream)

      val lines = source.getLines().toList

      val headers = lines.head.split(",").map(_.trim)

      val featureIndex = headers.indexOf(featureColumn)
      val targetIndex = headers.indexOf(targetColumn)

      if (featureIndex == -1 || targetIndex == -1) {
        println("Required column not found.")
        return
      }

      val data = lines.tail
        .map(_.split(",").map(_.trim))
        .filter(row =>
          row.length > math.max(featureIndex, targetIndex) &&
            row(featureIndex).toDoubleOption.isDefined &&
            row(targetIndex).toDoubleOption.isDefined
        )
        .map(row =>
          (row(featureIndex).toDouble, row(targetIndex).toDouble)
        )

      val smallData = data.take(20)

      val targetValues = smallData.map(_._2).sorted
      val median = targetValues(targetValues.length / 2)

      val xData = smallData.map(_._1)
      val labels = smallData.map { case (_, y) =>
        if (y >= median) 1.0 else 0.0
      }

      val x = DenseVector(xData.toArray)
      val y = DenseVector(labels.toArray)

      var weight = 0.0
      var bias = 0.0

      val learningRate = 0.01
      val iterations = 1000

      for (_ <- 0 until iterations) {

        var weightGradient = 0.0
        var biasGradient = 0.0

        for (i <- 0 until x.length) {

          val prediction = sigmoid(weight * x(i) + bias)

          val error = prediction - y(i)

          weightGradient += error * x(i)
          biasGradient += error
        }

        weightGradient /= x.length
        biasGradient /= x.length

        weight -= learningRate * weightGradient
        bias -= learningRate * biasGradient
      }

      println()
      println("Model Results")
      println("--------------------------------")
      println(f"Weight : $weight%.4f")
      println(f"Bias   : $bias%.4f")
      println(f"Median used for classification : $median%.2f")

      println()
      println("Classification Results")
      println("--------------------------------")

      for (i <- 0 until x.length) {

        val probability =
          sigmoid(weight * x(i) + bias)

        val predictedClass =
          if (probability >= 0.5) 1 else 0

        println(
          f"ARG = ${x(i)}%.2f  " +
            f"Actual = ${y(i).toInt}  " +
            f"Probability = $probability%.4f  " +
            s"Predicted = $predictedClass"
        )
      }

      val newValue = x(0)

      val probability =
        sigmoid(weight * newValue + bias)

      val prediction =
        if (probability >= 0.5) 1 else 0

      println()
      println("New Prediction")
      println("--------------------------------")
      println(f"ARG value       : $newValue%.2f")
      println(f"Probability     : $probability%.4f")
      println(s"Predicted class : $prediction")

      source.close()

    } catch {
      case e: Exception =>
        println("Error: " + e.getMessage)
    }
  }
}