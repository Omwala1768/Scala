import breeze.linalg._

object Question4 extends App {

  val matrix = DenseMatrix(
    (42.0, 58.0, 37.0),
    (65.0, 31.0, 72.0),
    (54.0, 49.0, 68.0)
  )

  val transposeMatrix = matrix.t
  val determinant = det(matrix)

  println("========== Movie Ticket Sales Matrix ==========")

  println("\nOriginal Matrix:")
  println(matrix)

  println("\nTranspose of Matrix:")
  println(transposeMatrix)

  println("\nDeterminant:")
  println(determinant)
}