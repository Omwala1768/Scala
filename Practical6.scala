import breeze.linalg._

object Practical6 {

  def main(args: Array[String]): Unit = {

    val matrix = DenseMatrix(
      (10, 20, 30, 40),
      (50, 60, 70, 80),
      (90, 100, 110, 120),
      (130, 140, 150, 160)
    )

    println("Original Matrix:")
    println(matrix)

    val subMatrix = matrix(1 to 2, 1 to 2)

    println("\nSub-Matrix:")
    println(subMatrix)

    val rowSums = sum(subMatrix(*, ::))
    println("\nRow Sums:")
    println(rowSums)

    val columnSums = sum(subMatrix(::, *))
    println("\nColumn Sums:")
    println(columnSums)

  }

}