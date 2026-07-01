object Question1 extends App {

  val ticketsSold = List(
    42, 58, 37, 65, 31,
    72, 54, 49, 68, 45,
    81, 76, 52, 63, 47,
    70, 59, 42, 78, 55
  )

  // Mean
  val mean = ticketsSold.sum.toDouble / ticketsSold.length

  // Median
  val sortedList = ticketsSold.sorted
  val n = sortedList.length

  val median: Double =
    if (n % 2 == 0)
      (sortedList(n / 2 - 1) + sortedList(n / 2)) / 2.0
    else
      sortedList(n / 2).toDouble

  // Mode
  val mode = ticketsSold
    .groupBy(identity)
    .maxBy(_._2.size)
    ._1

  // Display Results
  println("========== Multiplex Cinema ==========")
  println("Movie Ticket Sales in 20 Days:")
  println(ticketsSold.mkString(", "))

  println("\nStatistical Calculations")
  println("------------------------")
  println(f"Mean   : $mean%.2f")
  println(f"Median : $median%.2f")
  println(s"Mode   : $mode")
}