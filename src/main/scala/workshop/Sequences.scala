package workshop

import scala.annotation.tailrec

/**
  * In this set of exercise, we will implement functions operating on Scala Sequences (Seq), familiarizing ourselves
  * with the way Scala goes about working with Collections.
  */
object Sequences {

  /**
    * Implement a tail function, which returns the input Sequence without its first element (its 'head').
    * This remainder is called the 'tail' of the Sequence.
    *
    * @param seq The input sequence
    * @return The tail of the sequence
    */
  def tail[A](seq: Seq[A]): Seq[A] = ???

  /**
    * Implement a drop function, which returns a list with its first N elements removed
    *
    * @param n   The count of elements to drop
    * @param seq The input sequence
    * @return The remainder of the sequence
    */
  def drop[A](seq: Seq[Any], n: Int): Seq[Any] = ???

  /**
    * Implement a dropWhile function. This function should drop elements from a sequence which conform to a given
    * predicate, starting with the first element. Once an element does not conform to said predicate, stop removing
    * elements and return the remainder of the sequence.
    *
    * @param seq The input sequence
    * @param predicate The predicate
    * @return The remainder of the sequence
    */
  def dropWhile[A](seq: Seq[A], predicate: A => Boolean): Seq[A] = ???

  /*
  TDD exercise:
  Write a function 'map', which applies a given function (A => B) to each element in the Sequence and returns a new
  Sequence containing the results.
  Try defining the function signature yourself!
   */

  /*
  TDD exercise:
  Write a function 'filter' which checks each element of a sequence against a given predicate (A => Boolean). It returns
  a Sequence containing only those elements for which the predicate returns 'true'.
    Try defining the function signature yourself!
   */

  /*
  TDD exercise:
  Write a function 'flatten', which reduces the level of nesting in a Sequence of Sequences by concatenating all nested
  Sequences:
   */
  def flatten[A](seq: Seq[Seq[A]]): Seq[A] = ???

  /*
  TDD exercise:
  Write a function 'flatMap', which applies a function yielding a Sequence to each element in the sequence and returns
  the concatenation of these Sequences.
  Hint: it can be thought of as subsequent calls to 'map' and 'flatten', although that is not the most efficient way
  to implement it.
   */
  def flatMap[A,B](seq: Seq[A])(f: A => Seq[A]): Seq[A] = ???

  /*
  There is a more general way to operate on Sequences than the previously implemented functions. These operations are
  called 'folds'. Scala Sequences have thee types of fold methods: fold, foldLeft, and foldRight. They mainly differ in
  the direction they iterate through the Sequence: foldRight works from right to left, and foldLeft the other way around.
  The direction of 'fold' is not defined.

  For most operations, you will be able to use one of the many the higher level methods (like 'map' or 'dropWhile')
  Scala Seq provides. However, some low-level APIs might force you to think in terms of a fold.

  Exercise 1: Look at the following sample implementations of foldLeft and foldRight. What issue could arise from the
  use of foldRight?

  Exercise 2: Given the limitation of foldRight, is there a way to implement foldRight by using foldLeft? Which
  disadvantage does this entail?

  Exercise 3: Implement the previous 'filter' and 'flatten' functions using a fold.
   */
  @tailrec
  def foldLeft[A, B](init: B)(f: (A, B) => B)(seq: Seq[A]): B = seq match {
    case Nil => init
    case x :: xs => foldLeft(f(x, init))(f)(xs)
  }

  def foldRight[A, B](init: B)(f: (A, B) => B)(seq: Seq[A]): B = seq match {
    case Nil => init
    case x :: xs => f(x, foldRight(init)(f)(xs))
  }
}
