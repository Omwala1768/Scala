import java.time.LocalDate
import scala.util.Random

object omwala_M2_pract14 {

  case class FootballData(date: LocalDate, goals: Int)

  def main(args: Array[String]): Unit = {

    val startDate = LocalDate.of(2026, 9, 1)
    val random = new Random()

    val data = (0 until 30).map { day =>
      val date = startDate.plusDays(day)
      val goals = random.nextInt(7)
      FootballData(date, goals)
    }

    println("Daily Football Goals - September 2026")
    println("--------------------------------------")

    data.foreach { d =>
      println(s"${d.date} : ${d.goals} goals")
    }

    val totalGoals = data.map(_.goals).sum
    val averageGoals = totalGoals.toDouble / data.length
    val minimumGoals = data.map(_.goals).min
    val maximumGoals = data.map(_.goals).max

    println()
    println("Basic Time Series Analysis")
    println("---------------------------")
    println(s"Total Goals   : $totalGoals")
    println(f"Average Goals : $averageGoals%.2f")
    println(s"Minimum Goals : $minimumGoals")
    println(s"Maximum Goals : $maximumGoals")
  }
}