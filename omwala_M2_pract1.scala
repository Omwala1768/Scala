import scala.io.Source

object omwala_M2_pract1 {

  def main(args: Array[String]): Unit = {

    val stream = getClass.getResourceAsStream("/fifa.csv")

    if (stream == null) {
      println("Error: File not found in resources folder!")
      return
    }

    val file = Source.fromInputStream(stream)

    val data = file.getLines().drop(1).flatMap { line =>
      val cols = line.split(",")

      for {
        x <- cols(2).trim.toDoubleOption
        y <- cols(3).trim.toDoubleOption
      } yield (x, y)
    }.toList

    file.close()

    val (x, y) = data.unzip

    val n = x.length.toDouble

    val meanX = x.sum / n
    val meanY = y.sum / n

    val num = x.zip(y).map {
      case (xi, yi) =>
        (xi - meanX) * (yi - meanY)
    }.sum

    val den = math.sqrt(
      x.map(xi => math.pow(xi - meanX, 2)).sum *
        y.map(yi => math.pow(yi - meanY, 2)).sum
    )

    val r =
      if (den == 0) 0.0
      else num / den

    val relation =
      if (r > 0.7) "Strong Positive"
      else if (r > 0) "Weak Positive"
      else "Negative"

    val df = n - 2

    val tStat =
      if (math.abs(r) == 1.0)
        Double.PositiveInfinity
      else
        r * math.sqrt(df / (1 - r * r))

    val isSignificant = math.abs(tStat) > 1.96

    println(s"Dataset Size: ${n.toInt} records")
    println(f"Pearson Correlation (r): $r%.4f ($relation Relationship)")
    println(f"t-Statistic: $tStat%.4f | Significant at 5%% level: $isSignificant")
  }
}