import com.github.tototoshi.csv._
import java.io.File
object omwala_practical10 {
  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("Player_Attributes.csv"))
    val data = reader.allWithHeaders()
    reader.close()
    val threshold = 95

    val filteredRows1 = data.filter { row =>
      row.get("strength").exists(value => value.toIntOption.exists(_ >
        threshold))
    }
    println(s"\nTotal Rows with strength > $threshold: ${filteredRows1.length}\n")
    filteredRows1.foreach { row =>
      println(row.values.mkString(", "))
    }

    val filteredRows2 = data.filter { row =>
      row.get("stamina").exists(value => value.toIntOption.exists(_ >
        threshold))
    }
    println(s"\nTotal Rows with stamina > $threshold: ${filteredRows2.length}\n")
    filteredRows2.foreach { row =>
        println(row.values.mkString(", "))
    }
  }
}