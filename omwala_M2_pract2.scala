import scala.io.Source
import breeze.plot._

object omwala_M2_pract2 {

  def main(args: Array[String]): Unit = {
    val stream = getClass.getResourceAsStream("/fifa.csv")

    if (stream == null) {
      println("Error: File not found in resources folder!")
      return
    }
    val file = Source.fromInputStream(stream)
    val lines = file.getLines().drop(1)
    val data = lines.flatMap { line =>
      val cols = line.split(",")
      cols(2).trim.toDoubleOption
    }.toList

    file.close()

    println("Original Data:")
    println(data.mkString(", "))
    
    val cleanData = data.filter(!_.isNaN)

    val window = 3
    
    val sma = cleanData.sliding(window).map(_.sum / window).toList
    
    val weights = List(1, 2, 3)
    val weightSum = weights.sum.toDouble

    val wma = cleanData.sliding(window).map { values =>
      values.zip(weights).map {
        case (v, w) => v * w
      }.sum / weightSum
    }.toList
    
    val alpha = 2.0 / (window + 1)

    var ema = List(cleanData.head)

    for (i <- 1 until cleanData.length) {
      val value = alpha * cleanData(i) + (1 - alpha) * ema.last
      ema = ema :+ value
    }

    println("\nSimple Moving Average")
    println(sma.mkString(", "))

    println("\nWeighted Moving Average")
    println(wma.mkString(", "))

    println("\nExponential Moving Average")
    println(ema.mkString(", "))

    val fig = Figure("Moving Average")
    val plt = fig.subplot(0)

    plt += plot((0 until cleanData.length).map(_.toDouble), cleanData)
    plt += plot((window - 1 until cleanData.length).map(_.toDouble), sma)

    plt += plot((window - 1 until cleanData.length).map(_.toDouble), wma)

    plt += plot((0 until ema.length).map(_.toDouble), ema)

    plt.title = "Moving Average Comparison"

    fig.refresh()

  }

}