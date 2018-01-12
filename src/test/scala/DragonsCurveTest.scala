import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class DragonsCurveTest extends FlatSpec with Matchers with TableDrivenPropertyChecks {

  val testCases = Table(
    ("iterations", "result"),
    (0, "F"),
    (1, "FRFR"),
    (2, "FRFRRLFLFR"),
    (3, "FRFRRLFLFRRLFRFRLLFLFR"),
    (5, "FRFRRLFLFRRLFRFRLLFLFRRLFRFRRLFLFRLLFRFRLLFLFRRLFRFRRLFLFRRLFRFRLLFLFRLLFRFRRLFLFRLLFRFRLLFLFR")
  )

  "A Dragon's Curve" should "be created" in {

    forAll(testCases) { (iterations, expected) =>
      DragonsCurve.create(iterations) shouldBe expected
    }
  }

}
