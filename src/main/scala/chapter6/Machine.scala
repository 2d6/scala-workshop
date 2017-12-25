package chapter6

import chapter6.Machine.Input

case class Transition[S, +A](accept: S => (A, S)) {

  def map[B](f: A => B): Transition[S, B] = flatMap(a => Transition.unit(f(a)))

  //  def map2[B,C](sb: Transition[S, B])(f: (A, B) => C): Transition[S, C] = Transition {
  //    s =>
  //      val ax = accept(s)
  //      val bx = accept(s)
  //      (f(ax, bx), s)
  //  }
  def map2[B, C](sb: Transition[S, B])(f: (A, B) => C): Transition[S, C] = flatMap {
      a => sb.map(f(a, _))
  }

  def flatMap[B](f: A => Transition[S, B]): Transition[S, B] = Transition {
    s =>
      val a = accept(s)._1
      f(a).accept(s)
  }
}

object Transition {

  def unit[S, A](a: A): Transition[S, A] = Transition(s => (a, s))

  def sequence[S, A](seq: Seq[Transition[S,A]]): Transition[S, Seq[A]] = Transition {
    s =>
      (seq.flatMap(t => Seq(t.accept(s)._1)), s)
  }
}


object Machine {

  sealed trait Input

  case object Coin extends Input

  case object Turn extends Input

}

case class Machine(locked: Boolean, candies: Int, coins: Int) {
  def simulateMachine(inputs: List[Input]): Transition[Machine, (Int, Int)] = ???
}


object Runner extends App {

  val printer = Transition[Int, String](i => (i.toString, i))
  println(printer.map(s => s + s).accept(23))

  println(printer.flatMap(s => Transition(i => ("herbert" + s, i))).accept(23))
}