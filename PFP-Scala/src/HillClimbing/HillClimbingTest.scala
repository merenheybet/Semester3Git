import org.junit.Assert._
import org.junit.Test

class HillClimbingTest {

  // A solution for the function 1 / (2x^2 + 3y^2 + 1) + 1 / (3x^2 + y^2 + 2)
  class Function2DSolution(x: Double, y: Double) extends Solution {
    def quality: Double = 1.0 / (2 * x * x + 3 * y * y + 1) + 1.0 / (3 * x * x + y * y + 2)

    def neighbors: List[Solution] = {
      for (dx <- List(-1.0, 1.0); dy <- List(-1.0, 1.0))
        yield new Function2DSolution(x + dx, y + dy)
    }

    def coordinates: (Double, Double) = (x, y)
  }



  // A solution for the Travelling Salesperson Problem
  class TSPSolution(graph: List[List[Int]], order: List[Int]) extends Solution {
    def quality: Double = -(0 :: order).zip(order :+ 0)
      .map(edge => graph(edge._1)(edge._2))
      .sum

    def neighbors: List[Solution] = swapNeighbors(order).map(s => new TSPSolution(graph, s))

    def getOrder: List[Int] = order
  }





  @Test(timeout = 1000)
  def testSwapNeighborsExerciseSheetExample: Unit = {
    assertEquals("swapNeighbors(List(1,2,3)) returns wrong result",
      List(List(2,1,3), List(3,2,1), List(1,3,2)),
      swapNeighbors(List(1,2,3)))
  }



  @Test(timeout = 4000)
  def testHillClimbingOn2DFunction: Unit = {
    // The local maximum of the function defined above is at (0, 0).
    // Hill Climbing should be able to find this maximum.
    assertEquals("Hill Climbing does not find expected optimum",
      (0.0, 0.0),
      optimum(hillClimbing(new Function2DSolution(-3, 3)))
        .asInstanceOf[Function2DSolution].coordinates)
  }



  @Test(timeout = 4000)
  def testHillClimbingOnTSP: Unit = {
    // The TSP problem tries to find the shortest tour in a graph.
    // Hill Climbing should be able to find the shortest tour in the graph
    // given below.
    val graph: List[List[Int]] = List(
      List(0, 1, 2, 3), List(2, 0, 3, 1), List(1, 3, 0, 2), List(3, 2, 1, 0))

    val start: TSPSolution = new TSPSolution(graph, List(3, 2, 1)) // 0->3->2->1->0

    assertEquals("Hill climbing does not find expected optimum",
      List(1, 3, 2), // 0->1->3->2->0
      optimum(hillClimbing(start)).asInstanceOf[TSPSolution].getOrder)
  }
}
