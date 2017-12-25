import scala.annotation.tailrec

object IsSorted extends App {

  val sorted: Seq[Int] = (1 to 10).toList
  val unsorted = sorted.reverse

  @tailrec
  def isSorted[A](seq: Seq[A])(implicit comparator: ((A, A) => Boolean)): Boolean = {
    seq match {
      case Nil => true
      case _ :: Nil => true
      case x :: xs => comparator(x, xs.head) && isSorted(xs)(comparator)
    }
  }
  
  def foo[A](seq: Seq[A])(implicit comparator: (A, A) => Boolean): Boolean = {
    val initial: (Boolean, Option[A]) = (true, None)
    seq.foldLeft(initial)((t, e) => (t._1 && t._2.forall(comparator(_, e)), Some(e)))._1
  }

  implicit val comparator: (Int, Int) => Boolean = _ <= _
  println(foo(Nil))
  println(foo(Seq(1)))
  println(foo(sorted))
  println(foo(unsorted))
}