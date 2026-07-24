import breeze.linalg._
import breeze.plot._
import com.github.tototoshi.csv._
import java.io.File
object omwala_practical13 {
  def main(args: Array[String]): Unit = {
    val reader = CSVReader.open(new File("Tariff.csv"))
    val data = reader.allWithHeaders()

    reader.close()
    
    val Samsung = data.filter(_("Brand Name") == "Samsung")
    val Levis = data.filter(_("Brand Name") == "Levi's")
    val Zara = data.filter(_("Brand Name") == "Zara")
    def extractXY(rows: List[Map[String, String]]) = {
      val x = DenseVector(rows.map(_("Price Before Tariff").toDouble).toArray)
      val y = DenseVector(rows.map(_("Price After Tariff").toDouble).toArray)
      (x, y)
    }
    val (xSamsung, ySamsung) = extractXY(Samsung)
    val (xLevis, yLevis) = extractXY(Levis)
    val (xZara, yZara) = extractXY(Zara)
    
    val fig = Figure()
    val plt = fig.subplot(0)
    plt.title = "Price Before Tariff vs Price After Tariff"
    plt.xlabel = "Before Tariff"
    plt.ylabel = "After Tariff"
    plt += plot(xSamsung, ySamsung, '.', name = "Samsung", colorcode = "blue")
    plt += plot(xLevis, yLevis, '.', name = "Levis", colorcode =
      "green")
    plt += plot(xZara, yZara, '.', name = "Zara", colorcode =
      "red")
    fig.refresh()
  }
}