package chapter4

import scala.annotation.tailrec

object Option {

  def map2[A,B,C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] =
    a.flatMap(aget =>
      b.map(bget => f(aget, bget)))

  def seq[A](a: Seq[Option[A]]): Option[Seq[A]] = seqRecurse(a)

  @tailrec
  private def seqRecurse[A](a: Seq[Option[A]], acc: Seq[A] = Nil): Option[Seq[A]] = a match {
    case Nil => Some(acc.reverse)
    case None :: _ => None
    case Some(get) :: xs => seqRecurse(xs, get +: acc)
  }

  def traverse[A, B](a: Seq[A])(f: A => Option[B]): Option[Seq[B]] = traverseRecurse(a)(f)

  @tailrec
  private def traverseRecurse[A,B](a: Seq[A], acc: Seq[B] = Nil)(f: A => Option[B]): Option[Seq[B]] = a match {
    case Nil => Some(acc)
    case None :: _ => None
    case x :: xs => f(x) match {
      case Some(get) => traverseRecurse(xs, acc :+ get)(f)
      case None => None
    }
  }
}

sealed trait Option[+A] {

  def map[B](f: A => B): Option[B] = this match {
    case Some(get) => Some(f(get))
    case None => None
  }

  def flatMap[B](f: A => Option[B]): Option[B] = map(f).getOrElse(None)

  def getOrElse[B >: A](default: => B): B = this match {
    case Some(get) => get
    case None => default
  }

  def orElse[B >: A](ob: => Option[B]): Option[B] = map(Some(_)).getOrElse(ob)

  def filter(f: A => Boolean): Option[A] = flatMap(get => if (f(get)) this else None)
}

case class Some[+A](get: A) extends Option[A]

case object None extends Option[Nothing]

//sealed trait Option[+A] {
//
//  def map[B](f: A => B): Option[B]
//  def flatMap[B](f: A => Option[B]): Option[B]
//  def getOrElse[B >: A](default: => B): B
//  def orElse[B >: A](ob: => Option[B]): Option[B]
//  def filter(f: A => Boolean): Option[A]
//}
//
//case class Some[+A](get: A) extends Option[A] {
//  override def map[B](f: A => B): Option[B] = Some(f(get))
//
//  override def flatMap[B](f: A => Option[B]): Option[B] = f(get)
//
//  override def getOrElse[B >: A](default: => B): B = get
//
//  override def orElse[B >: A](ob: => Option[B]): Option[B] = this
//
//  override def filter(f: A => Boolean): Option[A] = if (f(get)) this else None
//}
//
//case object None extends Option[Nothing] {
//  override def map[B](f: Nothing => B): Option[B] = None
//
//  override def flatMap[B](f: Nothing => Option[B]): Option[B] = None
//
//  override def getOrElse[B >: Nothing](default: => B): B = default
//
//  override def orElse[B >: Nothing](ob: => Option[B]): Option[B] = ob
//
//  override def filter(f: Nothing => Boolean): Option[Nothing] = None
//}
