import org.junit.Assert.assertEquals
import org.junit.Test

class BinaryTest {

  @Test(timeout = 500)
  def testParseLongSignature: Unit = {
    val f: List[Char] => Long = parseLong
  }



  @Test(timeout = 1000)
  def testParseLongExample: Unit = {
    assertEquals("parseLong(List('1', '0', '1', '0', '1', '0')) returns wrong result",
        42, parseLong(List('1', '0', '1', '0', '1', '0')))
  }
}
