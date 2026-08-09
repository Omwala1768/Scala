import scala.io.Source

object omwala_M2_pract3 {

  def main(args: Array[String]): Unit = {

    println("Om Wala S119")
    println("Frequency Distribution and Cumulative Frequency")
    println("-----------------------------------------------")

    val fileName = "/fifa.csv"

    val columnName = "ARG"

    try {

      val stream = getClass.getResourceAsStream(fileName)

      if (stream == null) {
        println(s"File '$fileName' not found in resources folder.")
        return
      }

      val source = Source.fromInputStream(stream)

      val lines = source.getLines().toList

      val header = lines.head.split(",").map(_.trim)

      val columnIndex = header.indexOf(columnName)

      if (columnIndex == -1) {
        println(s"Column '$columnName' not found in CSV file.")
      } else {

        val data = lines.tail
          .map(_.split(",").map(_.trim))
          .filter(row => row.length > columnIndex)
          .flatMap(row => row(columnIndex).toDoubleOption)

        val frequency = data
          .groupBy(identity)
          .view
          .mapValues(_.size)
          .toMap

        println()
        println(f"${"Value"}%-15s${"Frequency"}%-15s${"Cumulative Frequency"}")
        println("------------------------------------------------------")

        var cumulativeFrequency = 0

        frequency.toSeq.sortBy(_._1).foreach {
          case (value, freq) =>

            cumulativeFrequency += freq

            println(
              f"$value%-15.2f$freq%-15d$cumulativeFrequency"
            )
        }

        println("------------------------------------------------------")
        println(s"Total Observations: ${data.size}")
      }

      source.close()

    } catch {
      case e: Exception =>
        println("Error: " + e.getMessage)
    }
  }
}