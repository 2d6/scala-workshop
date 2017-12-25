package chapter5

import org.scalatest.{FlatSpec, Matchers}

class StreamTest extends FlatSpec with Matchers {

  "toList" should "return a List" in {

    Stream(1,2,3).toList shouldBe a[List[_]]
  }

  it should "return a List with the same elements" in {

    Stream(1,2,3).toList shouldBe List(1,2,3)
  }

  it should "return an empty List if the Stream is empty" in {

    Stream.empty.toList shouldBe Nil
  }

  "take" should "return a limited amount of elements" in {

    Stream(1,2,3).take(1).toList.length shouldBe 1
  }

  "takeWhile" should "return a limited amount of elements" in {

    Stream(1,2,3).takeWhile(_ < 3).toList shouldBe List(1,2)
  }

  "forAll" should "return true if the predicate matches all elements" in {

    Stream(1,2,3).forAll(_ < 4) shouldBe true
  }

  it should "return false if the predicate matches no elements" in {

    Stream(1,2,3).forAll(_ > 4) shouldBe false
  }

  it should "return false if the predicate returns false for any element" in {

    Stream(1,2,3).forAll(_ < 3) shouldBe false
  }

  "headOption" should "return a Some containing the first element" in {

    Stream(1,2,3).headOption shouldBe Some(1)
  }

  it should "return None if the Stream is empty" in {

    Stream.empty[Int].headOption shouldBe None
  }

  "map" should "map all of the elements" in {

    Stream(1,2,3).map(_.toString).toList shouldBe List("1", "2", "3")
  }

  it should "return an empty Stream in case of empty input" in {

    Stream.empty[Int].map(_.toString).toList shouldBe List.empty[String]
  }

  "filter" should "remove invalid elements" in {

    Stream(1,2,3).filter(_ % 2 == 0).toList shouldBe List(2)
  }

  it  should "return an empty Stream in case of empty input" in {

    Stream.empty[Int].filter(_ % 2 == 0).toList shouldBe List.empty[Int]
  }

  "append" should "append two Streams" in {

    Stream(1).append(Stream(2)).toList shouldBe List(1,2)
  }

  it should "append two Streams of different types" in {

    Stream(1).append(Stream(2.0)).toList shouldBe List(1,2.0)
  }

  it should "be lazy in its argument" in {
    lazy val other: Stream[Int] = throw new IllegalArgumentException

    Stream(1).append(other).find(_ < 2).toList shouldBe List(1).toStream
  }

  "flatMap" should "map and flatten Streams" in {

    Stream(1,2).flatMap(i => Stream(s"$i", s"$i")).toList shouldBe List("1", "1", "2", "2")
  }

  it should "map to an empty Stream and flatten" in {

    Stream(1,2).flatMap(_ => Stream.empty[Int]).toList shouldBe List.empty[Int]
  }

  it should "map empty Streams" in {

    Stream.empty[Int].flatMap(_ => Stream(1)).toList shouldBe List.empty[Int]
  }

  "from" should "return an incrementing Stream of Ints" in {

    Stream.from(5).take(5).toList shouldBe List(5,6,7,8,9)
  }

  "fibs" should "create a Stream of Fibonacci numbers" in {

    Stream.fibs.take(5).toList shouldBe List(0,1,1,2,3)
  }

  "fibs via unfold" should "create a Stream of Fibonacci numbers" in {

    Stream.fibsViaUnfold.take(5).toList shouldBe List(0,1,1,2,3)
  }

  "from via unfold" should "return an incrementing Stream of Ints" in {

    Stream.fromViaUnfold(5).take(5).toList shouldBe List(5,6,7,8,9)
  }

  "constant via unfold" should "return the same element over and over" in {

    Stream.constantViaUnfold(2).take(3).toList shouldBe List(2,2,2)
  }

  "ones via unfold" should "return the same element over and over" in {

    Stream.onesViaUnfold.take(3).toList shouldBe List(1,1,1)
  }

  "take via unfold" should "return a limited amount of elements" in {

    Stream(1,2,3).takeViaUnfold(1).toList.length shouldBe 1
  }

  "takeWhile via unfold" should "return a limited amount of elements" in {

    Stream(1,2,3).takeWhileViaUnfold(_ < 3).toList shouldBe List(1,2)
  }

  "zipWith" should "return a zipped Stream" in {

    Stream(1,2).zipWith(Stream("A", "B"))((a, b) => b + a).toList shouldBe List("A1", "B2")
  }

  it should "work with Streams of different length" in {

    Stream.ones.zipWith(Stream("A", "B"))((a, b) => b + a).toList shouldBe List("A1", "B1")
  }

  "zipAll" should "return a zipped Stream of Options" in {

    Stream(1,2).zipAll(Stream("A", "B")).toList shouldBe List((Some(1), Some("A")), (Some(2), Some("B")))
  }

  it should "work with Streams of different length" in {

    Stream.ones.zipAll(Stream("A", "B")).take(2).toList shouldBe List((Some(1), Some("A")), (Some(1), Some("B")))
  }

  "startsWith" should "return true if one Stream is the prefix of another" in {

    Stream(1,2,3).startsWith(Stream(1,2)) shouldBe true
  }

  it should "return false if one Stream is not the prefix of another" in {

    Stream(1,2,3).startsWith(Stream(1,3)) shouldBe false
  }

  it should "return false if the supposed prefix Stream is longer than the original one" in {

    Stream(1,2,3).startsWith(Stream(1,2,3,4)) shouldBe false
  }


}
