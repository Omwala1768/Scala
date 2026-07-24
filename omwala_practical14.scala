import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File
object omwala_practical14 {
  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("Tariff.csv"))
    val data = reader.allWithHeaders()
    reader.close()

    val BeforeTariff =

      DenseVector(data.map(_("Price Before Tariff").toDouble).toArray)

    val fig = Figure("Histogram of Price Before Tariff")
    val binSizes = List(5, 10, 20)
    for ((bins, idx) <- binSizes.zipWithIndex) {
      val plt = fig.subplot(1, binSizes.length, idx)
      plt += hist(BeforeTariff, bins)
      plt.title = s"Histogram with $bins bins"
      plt.xlabel = "Price Before Tariff"
      plt.ylabel = "Frequency"
    }
    fig.refresh()
  }
}