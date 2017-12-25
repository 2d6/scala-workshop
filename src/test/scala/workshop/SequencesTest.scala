package workshop

import org.scalatest.prop.{TableDrivenPropertyChecks, Tables}
import org.scalatest.{FlatSpec, Matchers}

class SequencesTest extends FlatSpec with Matchers with TableDrivenPropertyChecks {

  import SequencesTest._

  "A tail function" should "remove the first element from a sequence" in {
    Sequences.tail(Seq(1, 2, 3)) shouldBe Seq(2, 3)
  }

  it should "return an empty sequence if the input sequence holds only one element" in {
    Sequences.tail(Seq(1)) shouldBe Nil
  }

  it should "remove the first element from a sequence of arbitrary type" in {
    forAll(differentlyTypedSequences) { (input, expected) =>
      Sequences.tail(input) shouldBe expected
    }
  }

  it should "return an empty sequence if the input sequence is Nil" in {
    Sequences.tail(Nil) shouldBe Nil
  }

  it should "work with very long sequences" in {
    val length = 100000
    val input = Seq.range(0, length)

    Sequences.tail(input) shouldBe input.tail
  }

  "A drop function" should "remove the first element from a sequence" in {
    Sequences.drop(Seq(1, 2, 3), 1) shouldBe Seq(2, 3)
  }

  it should "remove the first few elements from a sequence" in {
    Sequences.drop(Seq(1, 2, 3), 2) shouldBe Seq(3)
  }

  it should "remove all elements from a sequence" in {
    Sequences.drop(Seq(1, 2, 3), 3) shouldBe Nil
  }

  it should "remove all elements from a sequence if it wants to drop more elements from a sequence than it contains" in {
    Sequences.drop(Seq(1, 2, 3), 99) shouldBe Nil
  }

  it should "return an empty sequence if the input sequence is Nil" in {
    Sequences.drop(Nil, 99) shouldBe Nil
  }

  it should "remove the first element from a sequence of arbitrary type" in {
    forAll(differentlyTypedSequences) { (input, expected) =>
      Sequences.drop(input, 1) shouldBe expected
    }
  }

  it should "work with very long sequences" in {
    val length = 100000
    val input = Seq.range(0, length)

    Sequences.drop(input, length - 1) shouldBe Seq(length - 1)
  }

  val lessThanThree: Int => Boolean = _ < 3

  "A dropWhile function" should "remove elements while the predicate is true" in {
    Sequences.dropWhile(Seq(1, 2, 3), lessThanThree) shouldBe Seq(3)
  }

  it should "stop removing elements after the first element did not fulfil the predicate" in {
    Sequences.dropWhile(Seq(1, 2, 3, 2, 1), lessThanThree) shouldBe Seq(3, 2, 1)
  }

  it should "remove all elements if all elements fulfil the predicate" in {
    Sequences.dropWhile(Seq(1, 1, 1), lessThanThree) shouldBe Nil
  }

  it should "remove no elements if no elements fulfil the predicate" in {
    Sequences.dropWhile(Seq(3, 3, 3), lessThanThree) shouldBe Seq(3, 3, 3)
  }

  it should "return an empty Sequence if the input sequence is Nil" in {
    Sequences.dropWhile(Nil, lessThanThree) shouldBe Nil
  }

  it should "work for other types than Int" in {
    val herbert = "herbert"
    val bodo = "bodo"
    val isHerbert: String => Boolean = _ == herbert

    Sequences.dropWhile(Seq(herbert, herbert, bodo), isHerbert) shouldBe Seq(bodo)
  }

  it should "work with very long sequences" in {
    val length = 100000
    val input = Seq.range(0, length)
    val alwaysTrue: Int => Boolean = _ => true

    Sequences.dropWhile(input, alwaysTrue) shouldBe Nil
  }
}

object SequencesTest extends Tables {

  val differentlyTypedSequences = Table(
    ("input sequence", "expected sequence"),
    (Seq("a", "b", "c"), Seq("b", "c")),
    (Seq(1.0, 2.0, 3.0), Seq(2.0, 3.0)),
    (Seq(Some("a"), None), Seq(None))
  )
}
