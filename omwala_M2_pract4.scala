import scala.io.Source

object omwala_M2_pract4 {

  def main(args: Array[String]): Unit = {

    println("Om Wala S119")
    println("Top 5 Rows After Sorting FIFA Dataset")
    println("--------------------------------------")
    
    val fileName = "/fifa.csv"
    
    val sortColumn = "ARG"

    try {
      
      val stream = getClass.getResourceAsStream(fileName)

      if (stream == null) {
        println("CSV file not found in resources folder.")
        return
      }

      val source = Source.fromInputStream(stream)
      
      val lines = source.getLines().toList
      
      val headers = lines.head.split(",").map(_.trim)
      
      val columnIndex = headers.indexOf(sortColumn)

      if (columnIndex == -1) {
        println(s"Column '$sortColumn' not found.")
        return
      }
      
      val data = lines.tail
        .map(_.split(",").map(_.trim))
        .filter(row => row.length > columnIndex)
        .filter(row => row(columnIndex).toDoubleOption.isDefined)
      
      val sortedData = data.sortBy(
        row => -row(columnIndex).toDouble
      )
      
      val top5 = sortedData.take(5)

      println()
      println(s"Sorted by column: $sortColumn")
      println("Order: Descending")
      println()
      
      println(headers.mkString(" | "))
      println("-" * 80)

      
      top5.foreach { row =>
        println(row.mkString(" | "))
      }

      println()
      println("Top 5 rows extracted successfully.")

      source.close()

    } catch {
      case e: Exception =>
        println("Error: " + e.getMessage)
    }
  }
}
