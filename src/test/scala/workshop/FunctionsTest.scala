package workshop

import org.scalatest.{FlatSpec, Matchers}

class FunctionsTest extends FlatSpec with Matchers {

  "compose" should "return the composition of f and g" in {
    val f: Int => Double = _ * 1.5
    val g: Double => String = _.toString

    val h = Functions.compose(f, g)

    h(3) shouldBe "4.5"
  }

  "partial" should "insert the fixed value into f" in {
    val add: (Int, Int) => Int = _ + _

    val add5 = Functions.partial(add, 5)

    add5(10) shouldBe 15
    add5(1) shouldBe 6
  }


}
