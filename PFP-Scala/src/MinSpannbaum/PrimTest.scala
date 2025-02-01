import org.junit.Test
import org.junit.Assert.assertEquals

class PrimTest {

  val g1 = List(List((1,1), (3,5)), List((0,1), (2,2), (3,2)), List((1,2), (3,3)), List((0,5), (1,2), (2,3)))

  @Test(timeout = 1000)
  def testSignatures: Unit = {
    val f1: List[List[(Int, Int)]] => List[Int] => List[(Int, Int, Int)] = getPossibleEdges
    val f2: (List[Int] => List[(Int, Int, Int)]) => Int => List[(Int, Int)] = mst
  }

  @Test()
  def testMSTOnG1: Unit = assertEquals("mst returned wrong result for " + g1,
    Set((0, 1), (1, 2), (1, 3)),
    mst(getPossibleEdges(g1))(0).toSet)
}
