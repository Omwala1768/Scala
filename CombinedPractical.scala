import scala.util.Random
import scala.math.sqrt
import breeze.linalg._

object CombinedPractical extends App {

  // Generate random dataset of 20 numbers (20 to 100)
  val random = new Random()

  val data = List.fill(20)(random.nextInt(81) + 20)

  println("========================================")
  println(" RANDOM DATASET")
  println("========================================")
  println(data.mkString(", "))

  // -------------------------------------------------
  // QUESTION 1
  // Mean, Median and Mode
  // -------------------------------------------------

  val mean = data.sum.toDouble / data.length

  val sortedData = data.sorted
  val n = sortedData.length

  val median: Double =
    if (n % 2 == 0)
      (sortedData(n / 2 - 1) + sortedData(n / 2)) / 2.0
    else
      sortedData(n / 2).toDouble

  val mode = data
    .groupBy(identity)
    .maxBy(_._2.size)
    ._1

  println("\n========== QUESTION 1 ==========")
  println(f"Mean   : $mean%.2f")
  println(f"Median : $median%.2f")
  println(s"Mode   : $mode")

  // -------------------------------------------------
  // QUESTION 2
  // Variance and Standard Deviation
  // -------------------------------------------------

  val variance = data
    .map(x => math.pow(x - mean, 2))
    .sum / data.length

  val standardDeviation = sqrt(variance)

  println("\n========== QUESTION 2 ==========")
  println(f"Variance           : $variance%.2f")
  println(f"Standard Deviation : $standardDeviation%.2f")

  // -------------------------------------------------
  // QUESTION 3
  // Dense Vector
  // -------------------------------------------------

  val vector = DenseVector(data.map(_.toDouble).toArray)

  val weights = DenseVector(
    (1 to 20).map(_.toDouble).toArray
  )

  val vectorSum = sum(vector)
  val vectorMean = vectorSum / vector.length
  val dotProduct = vector dot weights

  println("\n========== QUESTION 3 ==========")
  println("Dense Vector:")
  println(vector)

  println(f"\nSum         : $vectorSum%.2f")
  println(f"Mean        : $vectorMean%.2f")
  println(f"Dot Product : $dotProduct%.2f")

  // -------------------------------------------------
  // QUESTION 4
  // 3 × 3 Matrix
  // -------------------------------------------------

  val matrix = DenseMatrix(
    (data(0).toDouble, data(1).toDouble, data(2).toDouble),
    (data(3).toDouble, data(4).toDouble, data(5).toDouble),
    (data(6).toDouble, data(7).toDouble, data(8).toDouble)
  )

  val transposeMatrix = matrix.t
  val determinant = det(matrix)

  println("\n========== QUESTION 4 ==========")

  println("\nOriginal Matrix:")
  println(matrix)

  println("\nTranspose Matrix:")
  println(transposeMatrix)

  println(f"\nDeterminant : $determinant%.2f")
}