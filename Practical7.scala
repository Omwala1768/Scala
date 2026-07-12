import breeze.linalg._

object Practical7{

  def main(args: Array[String]): Unit = {
    
    val matrix1 = DenseMatrix(
      (10.0, 20.0, 30.0),
      (40.0, 50.0, 60.0),
      (70.0, 80.0, 90.0)
    )
    
    val matrix2 = DenseMatrix(
      (1.0, 2.0, 3.0),
      (4.0, 5.0, 6.0),
      (7.0, 8.0, 9.0)
    )

    println("Matrix 1:")
    println(matrix1)

    println("\nMatrix 2:")
    println(matrix2)

    // Element-wise Addition
    val addition = matrix1 + matrix2
    println("\nElement-wise Addition:")
    println(addition)
    
    val subtraction = matrix1 - matrix2
    println("\nElement-wise Subtraction:")
    println(subtraction)
    
    val multiplication = matrix1 * matrix2
    println("\nElement-wise Multiplication:")
    println(multiplication)
    
    val division = matrix1 / matrix2
    println("\nElement-wise Division:")
    println(division)

  }

}