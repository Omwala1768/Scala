import breeze.linalg._
object Question3 extends App {
  val ticketsSold = DenseVector(
    42.0, 58.0, 37.0, 65.0, 31.0,
    72.0, 54.0, 49.0, 68.0, 45.0,
    81.0, 76.0, 52.0, 63.0, 47.0,
    70.0, 59.0, 42.0, 78.0, 55.0
  )

  // Second vector (same size) for dot product
  val weights = DenseVector(
    1.0, 2.0, 3.0, 4.0, 5.0,
    6.0, 7.0, 8.0, 9.0, 10.0,
    11.0, 12.0, 13.0, 14.0, 15.0,
    16.0, 17.0, 18.0, 19.0, 20.0
  )

  // Calculations
  val totalSum = sum(ticketsSold)
  val mean = totalSum / ticketsSold.length
  val dotProduct = ticketsSold dot weights

  // Display Results
  println("========== Multiplex Cinema ==========")
  println("Dense Vector:")
  println(ticketsSold)

  println("\nVector Calculations")
  println("------------------------")
  println(s"Sum         : $totalSum")
  println(f"Mean        : $mean%.2f")
  println(s"Dot Product : $dotProduct")

}
