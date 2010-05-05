import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import Operators._

class OperatorsTest extends FlatSpec with ShouldMatchers {
  "Multiply" should "multiply the 2 values" in {
    multiply(0)(0) should equal (0)
    multiply(2)(5) should equal (10)
  }
  "Double" should "double initial value" in {
    double(0) should equal (0)
    double(5) should equal (10)
  }
}