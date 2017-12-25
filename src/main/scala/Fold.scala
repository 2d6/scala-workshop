import scala.annotation.tailrec

object Fold extends App {

  @tailrec
  def foldLeft[A, B](init: B)(f: (A, B) => B)(seq: Seq[A]): B = seq match {
    case Nil => init
    case x :: xs => foldLeft(f(x, init))(f)(xs)
  }

  def foldRight[A, B](init: B)(f: (A, B) => B)(seq: Seq[A]): B = seq match {
    case Nil => init
    case x :: xs => f(x, foldRight(init)(f)(xs))
  }

  val f: (Int, Int) => Int = (i: Int, j: Int) => i * j
  val res: Int = foldLeft(1)(f)(Seq(1,2,3))
  println(res)

  val res2: Int = foldRight(1)(f)(Seq(1,2,3))
  println(res2)
}
