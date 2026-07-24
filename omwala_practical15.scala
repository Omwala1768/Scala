import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object omwala_practical15 {
  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("fifa.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val parsedData = data.flatMap { row =>
      try {
        val date = LocalDate.parse(row("Date"), dateFormatter)
        val goal = row("FRA").toDouble
        Some((date, goal))
      } catch {
        case _: Throwable => None
      }
    }.sortBy(_._1)
    val x = DenseVector((0 until
      parsedData.length).map(_.toDouble).toArray)
    val y = DenseVector(parsedData.map(_._2).toArray)
    val fig = Figure("Fifa - France Goal over Time")
    val plt = fig.subplot(0)
    plt += plot(x, y, name = "Goal", colorcode = "blue")
    plt.xlabel = "Time (Days)"
    plt.ylabel = "Goals"
    plt.title = "Fifa - France Over Time"
    fig.refresh()
  }
}
