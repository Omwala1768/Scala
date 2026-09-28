import scala.math.sqrt

object Question2 extends App {

  val ticketsSold = List(
    42, 58, 37, 65, 31,
    72, 54, 49, 68, 45,
    81, 76, 52, 63, 47,
    70, 59, 42, 78, 55
  )

  // Mean
  val mean = ticketsSold.sum.toDouble / ticketsSold.length

  // Variance
  val variance = ticketsSold
    .map(x => math.pow(x - mean, 2))
    .sum / ticketsSold.length

  // Standard Deviation
  val standardDeviation = sqrt(variance)

  // Display Results
  println("========== Multiplex Cinema ==========")
  println("Movie Ticket Sales in 20 Days:")
  println(ticketsSold.mkString(", "))

  println("\nStatistical Calculations")
  println("------------------------")
  println(f"Mean               : $mean%.2f")
  println(f"Variance           : $variance%.2f")
  println(f"Standard Deviation : $standardDeviation%.2f")
}
