package chapter4

import org.scalatest.{FlatSpec, Matchers}

class OptionTest extends FlatSpec with Matchers {

  "Some" should "apply a map" in {
    Some(1).map(_ + 1) shouldBe Some(2)
  }

  it should "apply a map changing the type" in {
    Some(1).map("herbert" + _) shouldBe Some("herbert1")
  }

  it should "apply an identity flatMap" in {
    Some(1).flatMap(Some(_)) shouldBe Some(1)
  }

  it should "apply a flatMap" in {
    Some(1).flatMap(i => Some(i + 1)) shouldBe Some(2)
  }


  it should "not return the getOrElse default value" in {
    Some(1).getOrElse(2) shouldBe 1
  }

  it should "not compute the getOrElse default value" in {
    Some(1).getOrElse(throw new IllegalArgumentException("This should not be evaluated"))
  }

  it should "not return the orElse value" in {
    Some(1).orElse(Some(2)) shouldBe Some(1)
  }

  it should "not compute the orElse value" in {
    Some(1).orElse(throw new IllegalArgumentException("This should not be evaluated")) shouldBe Some(1)
  }

  it should "return itself if a filter predicate evaluates true" in {
    Some(1).filter(_ == 1) shouldBe Some(1)
  }

  it should "return None if a filter predicate evaluates false" in {
    Some(1).filter(_ == 2) shouldBe None
  }

  "None" should "not apply a map" in {
    val none: Option[Int] = None

    none.map(_ + 1) shouldBe None
  }

  it should "not apply an identity flatMap" in {
    val none: Option[Int] = None

    none.flatMap(Some(_)) shouldBe None
  }

  it should "return the getOrElse default value" in {
    None.getOrElse(1) shouldBe 1
  }

  it should "return the orElse value" in {
    None.orElse(Some(1)) shouldBe Some(1)
  }

  it should "return None if a filter predicate evaluates true" in {
    None.filter(_ == 1) shouldBe None
  }

  it should "return None if a filter predicate evaluates false" in {
    None.filter(_ == 2) shouldBe None
  }

  "Option" should "apply a bifunction using map2()" in {
    Option.map2(Some("herbert"), Some(1))(_ + _) shouldBe Some("herbert1")
  }

  it should "return None if one of the Option arguments of map2() are None" in {
    val none: Option[Int] = None

    Option.map2(Some(1), none)(_ + _) shouldBe None

    Option.map2(none, Some(1))(_ + _) shouldBe None

    Option.map2(none, none)(_ + _) shouldBe None
  }

  it should "return Nil if the input to seq() is Nil" in {
    Option.seq(Nil) shouldBe Some(Nil)
  }

  it should "return None if the input to seq() only contains Nones" in {
    Option.seq(Seq(None)) shouldBe None

    Option.seq(Seq(None, None)) shouldBe None
  }

  it should "return None if the input to seq() contains None" in {
    Option.seq(Seq(Some(1), None)) shouldBe None
  }

  it should "return a Sequence of values if the input sequence to seq() only contains Some" in {
    Option.seq(Seq(Some(1))) shouldBe Some(Seq(1))

    Option.seq(Seq(Some(1), Some(2), Some(3))) shouldBe Some(Seq(1, 2, 3))
  }

  it should "return Nil if the input to traverse() is Nil" in {
    Option.traverse(Seq.empty[Int])(Some(_)) shouldBe Some(Nil)
  }

  it should "return the list of values when using traverse()" in {
    Option.traverse(Seq(1))(Some(_)) shouldBe Some(Seq(1))
  }

  it should "return None when using traverse() and all of the mapped values is None" in {
    Option.traverse(Seq(1))(_ => None) shouldBe None
  }

  it should "return None when using traverse() and at least one of the mapped values is None" in {
    Option.traverse(Seq(1, 2))(i => if (i < 1) Some(i) else None) shouldBe None
  }
}
