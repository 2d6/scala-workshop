
object Partial1 extends App {

  def partial1[A,B,C](a: A, f: (A,B) => C): B => C = f(a, _)

  implicit class PartialBiFunction[A,B,C](f: (A,B) => C) {
    def partial(a: A): B => C = f(a,_)
  }

  val g = (i: Int, s:String) => s + i

  println(partial1(10, g)("herbert"))
  println(partial1(10, g)("heinz"))

  println(g.partial(10)("hubert"))
}
