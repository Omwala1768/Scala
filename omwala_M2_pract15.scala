object omwala_M2_pract15 {

  def main(args: Array[String]): Unit = {

    val numbers = List(85, 91, 119)

    println("Original Dataset:")
    println(numbers)

    println("\nPolynomial Features up to Degree 3:")

    numbers.foreach { x =>
      val x1 = x
      val x2 = x * x
      val x3 = x * x * x

      println(s"$x -> [$x1, $x2, $x3]")
    }
  }
}