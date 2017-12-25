package chapter5

import chapter5.Stream._

import scala.annotation.tailrec

trait Stream[+A] {

  def foldRight[B](z: => B)(f: (A, => B) => B): B = // The arrow `=>` in front of the argument type `B` means that the function `f` takes its second argument by name and may choose not to evaluate it.
    this match {
      case Cons(h, t) => f(h(), t().foldRight(z)(f)) // If `f` doesn't evaluate its second argument, the recursion never occurs.
      case _ => z
    }

  def exists(p: A => Boolean): Boolean =
    foldRight(false)((a, b) => p(a) || b) // Here `b` is the unevaluated recursive step that folds the tail of the stream. If `p(a)` returns `true`, `b` will never be evaluated and the computation terminates early.

  @annotation.tailrec
  final def find(f: A => Boolean): Option[A] = this match {
    case Empty => None
    case Cons(h, t) => if (f(h())) Some(h()) else t().find(f)
  }

  def take(n: Int): Stream[A] = this match {
    case Cons(x, xs) => if (n == 0) Empty else Cons(x, () => xs().take(n - 1))
    case _ => Empty
  }

  def takeViaUnfold(n: Int): Stream[A] = unfold((this, n)) {
    case (_, 0) => None
    case (Cons(x, xs), remaining) => Some(x(), (xs(), remaining - 1))
    case _ => None
  }

  def drop(n: Int): Stream[A] = ???

  def takeWhile(p: A => Boolean): Stream[A] = this.foldRight(Stream.empty[A]) { (x, xs) =>
    if (p(x)) cons(x, xs) else Empty
  }

  def takeWhileViaUnfold(p: A => Boolean): Stream[A] = unfold(this) {
    case Cons(x, xs) if p(x()) => Some((x(), xs()))
    case _ => None
  }

  def forAll(p: A => Boolean): Boolean = this match {
    case Cons(x, xs) => p(x()) && xs().forAll(p)
    case Empty => true
  }

  def headOption: Option[A] = this.foldRight(Option.empty[A])((x, _) => Some(x))

  // 5.7 map, filter, append, flatmap using foldRight. Part of the exercise is
  // writing your own function signatures.

  def map[B](f: A => B): Stream[B] = this.foldRight(Stream.empty[B])((x, mapped) => cons(f(x), mapped))

  def filter(p: A => Boolean): Stream[A] = this.foldRight(Stream.empty[A])((x, filtered) => if (p(x)) cons(x, filtered) else filtered)

  def append[B >: A](s: => Stream[B]): Stream[B] = this.foldRight(s)((x, xs) => cons(x, xs))

  def flatMap[B](f: A => Stream[B]): Stream[B] = this.foldRight(Stream.empty[B]) { (x, mapped) => f(x).append(mapped) }

  def startsWith[B](s: Stream[B]): Boolean = zipAll(s).takeWhile(_._2.isDefined).forAll(t => t._1 == t._2)

  def toList: List[A] = {
    @tailrec
    def recurse(stream: Stream[A], list: List[A] = Nil): List[A] = stream match {
      case Cons(x, xs) => recurse(xs(), x() :: list)
      case Empty => list
    }

    recurse(this).reverse
  }

  def zipWith[B, C](other: Stream[B])(f: (A, B) => C): Stream[C] = unfold((this, other)) {
    case (Cons(ax, axs), Cons(bx, bxs)) => Some((f(ax(), bx()), (axs(), bxs())))
    case _ => None
  }

  def zipAll[B](other: Stream[B]): Stream[(Option[A],Option[B])] = unfold((this, other)) {
    case (Cons(ax, axs), Cons(bx, bxs)) => Some((Some(ax()), Some(bx())), (axs(), bxs()))
    case (Cons(ax, axs), Empty) => Some((Some(ax()), None), (axs(), Empty))
    case (Empty, Cons(bx, bxs)) => Some((None, Some(bx())), (Empty, bxs()))
    case _ => None
  }

}

object Runner extends App {

  val filter: Int => Boolean = { i =>
    val result = i > 0
    println(s"Filtering $i, result: $result")
    result
  }

  val mapper: Int => String = { i =>
    println(s"Mapping $i")
    i.toString
  }

  val ones: Stream[Int] = Stream.cons(1, ones)

  val filtered = ones.filter(filter)
  println("Filtered!")
  val mapped = filtered.map(mapper)
  println("Mapped!")
  val taken = mapped.take(5)
  println("Taken!")
  println(taken.toList)
}

case object Empty extends Stream[Nothing]

case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))

  val ones: Stream[Int] = constant(1)

  def constant[A](x: A): Stream[A] = cons(x, constant(x))

  def from(n: Int): Stream[Int] = cons(n, from(n + 1))

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = f(z) match {
    case Some((x, s)) => cons(x, unfold(s)(f))
    case None => Empty
  }

  def fibs: Stream[Int] = {

    def f(a: Int, b: Int): Stream[Int] = cons(a, f(b, a + b))

    f(0, 1)
  }

  def fibsViaUnfold: Stream[Int] = Stream(0, 1) append unfold((0, 1))(t => Some((t._1 + t._2, (t._2, t._1 + t._2))))

  def fromViaUnfold(n: Int): Stream[Int] = unfold(n)(x => Some((x, x + 1)))

  def constantViaUnfold[A](x: A): Stream[A] = unfold(x)(_ => Some(x, x))

  def onesViaUnfold: Stream[Int] = constantViaUnfold(1)

}