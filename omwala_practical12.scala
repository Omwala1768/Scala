import com.github.tototoshi.csv._
import java.io.File
object omwala_practical12 {
  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("Tariff.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    val categories = data.map(_("Product Type")).distinct.sorted
    val newData = data.map { row =>
      val product_type = row("Product Type")
      val oneHot = categories.map(cat => cat -> (if (cat == product_type) "1"
      else "0")).toMap
      (row - "Product Type") ++ oneHot
    }

    val headers = newData.head.keys.toList
    println(headers.mkString(", "))

    newData.foreach { row =>
      println(headers.map(row).mkString(", "))
    }

    val writer = CSVWriter.open(new File("Tariff_encoded.csv"))
    writer.writeRow(headers)
    newData.foreach(row => writer.writeRow(headers.map(row)))
    writer.close()
    println("One-hot encoded file written to Tariff_encoded.csv")
  }
}
