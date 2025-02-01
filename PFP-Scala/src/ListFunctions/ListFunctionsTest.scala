import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ListFunctionsTest {

  def testSignatures[E]: Unit = {
    val f0: (List[E], E) => List[Int] = find
    val f1: List[E] => List[List[E]] = powerSet
  }



  @Test(timeout = 1000)
  def testFindExample: Unit = {
    assertEquals("find(List('P', 'F', 'P'), 'P') returns a wrong result",
      List(0, 2),
      find(List('P', 'F', 'P'), 'P'))
  }



  @Test(timeout = 1000)
  def testPowerSetExample: Unit = {
    val result = powerSet(List('A', 'B'))
    println(result)
    val expected = List(Nil, List('A'), List('B'), List('A', 'B'))
    assertTrue("powerSet(List('A', 'B')) returns a wrong result expected:<" +
      expected.toString + "> but was:<" + result.toString + ">",
      expected == result.map(_.sorted).sortBy(_.foldLeft(0)(_*10+_-'A'+1)))
  }
}
