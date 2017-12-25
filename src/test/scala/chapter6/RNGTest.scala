package chapter6

import org.scalatest.{FlatSpec, Matchers}

class RNGTest extends FlatSpec with Matchers {

  case class TestRNG(value: Int) extends RNG {
    override def nextInt: (Int, RNG) = (value, this)
  }

  "nonNegativeInt" should "return a positive value if the RNG returns a positive number" in {
    RNG.nonNegativeInt(TestRNG(1))._1 shouldBe 1
  }

  it should "return a positive value if the RNG returns a negative number" in {
    RNG.nonNegativeInt(TestRNG(-1))._1 shouldBe 1
  }

  it should "return 0 if the RNG returns 0" in {
    RNG.nonNegativeInt(TestRNG(0))._1 shouldBe 0
  }

  case class IncrementingTestRNG(value: Int) extends RNG {
    override def nextInt: (Int, RNG) = (value, TestRNG(value + 1))
  }

  it should "return the next number if the RNG returns Integer.MIN_VALUE" in {
    val rng = IncrementingTestRNG(Integer.MIN_VALUE)
    RNG.nonNegativeInt(rng)._1 shouldBe Integer.MAX_VALUE
  }
}
