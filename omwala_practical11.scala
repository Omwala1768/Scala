import scala.io.Source
object omwala_practical11 {
  def main(args: Array[String]): Unit = {
    val filename = "om.txt"

    val lines = Source.fromFile(filename).getLines().toList

    val words = lines
      .flatMap(_.toLowerCase.split("\\W+"))
      .filter(_.nonEmpty)

    val wordcount = words.groupBy(identity).view.mapValues(_.size).toMap

    println("Word Frequency in om.txt file")
    wordcount.toSeq.sortBy(-_._2).foreach { case (word, count) =>
      println(f"$word%-15s -> $count")
    }
  }
}
