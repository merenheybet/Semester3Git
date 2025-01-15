import org.junit.Assert.assertEquals
import org.junit.Test


class CountingSortTest {

  @Test(timeout = 500)
  def testIncCountSignature: Unit = {
    val f: (Int, List[Int]) => List[Int] = incCount
  }



  @Test(timeout = 1000)
  def testIncCountExample: Unit = {
    assertEquals("incCount(0, Nil) returns wrong result", List(1), incCount(0, Nil))
    assertEquals("incCount(2, List(0, 1)) returns wrong result",
        List(0, 1, 1), incCount(2, List(0, 1)))
    assertEquals("incCount(1, List(0, 1, 1)) returns wrong result",
        List(0, 2, 1), incCount(1, List(0, 1, 1)))
  }



  @Test(timeout = 500)
  def testGetCountsSignature: Unit = {
    val f: List[Int] => List[Int] = getCounts
  }



  @Test(timeout = 1000)
  def testGetCountsExample: Unit = {
    assertEquals("getCounts(List(1, 2, 1)) returns wrong result",
        List(0, 2, 1), getCounts(List(1, 2, 1)))
  }



  @Test(timeout = 500)
  def testCountingSortSignature: Unit = {
    val f: List[Int] => List[Int] = countingSort
  }



  @Test(timeout = 2000)
  def testCountingSortExample: Unit = {
    assertEquals("countingSort(List(1, 2, 1, 7, 4, 7)) returns wrong result",
        List(1, 1, 2, 4, 7, 7), countingSort(List(1, 2, 1, 7, 4, 7)))
  }
}
