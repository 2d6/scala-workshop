import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class WordValidatorTest extends FlatSpec with Matchers with TableDrivenPropertyChecks {

  val validationCases = Table(
    ("word", "isValid"),
    ("abcabc", true),
    ("Abcabc", true),
    ("AbcCBa", true),
    ("?!?!?!", true),
    ("", true),
    ("a", true),
    ("aaaaaaa", true),
    ("AAAaa", true),
    ("AbcabcC", false),
    ("herbert", false),
    ("?abc:abc", false),
    ("aaaaab", false)
  )

  "A WordValidator" should "validate words with homogeneous and inhomogeneous character count" in {

    forAll(validationCases) { (word, isValid) =>
      WordValidator.validate(word) shouldBe isValid
    }
  }

}
