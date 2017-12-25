
object Curry extends App {

  def curry[A,B,C](f: (A, B) => C): A => (B => C) = (a: A) => f(a, _)

  implicit class CurriedBiFunction[A,B,C](f: (A, B) => C) {
    def mycurry: A => (B => C) = a => f(a, _)
  }

  implicit class Uncurried[A, B, C](f: A => (B => C)) {
    def myuncurry: (A, B) => C = (a: A, b: B) => f(a)(b)
  }

  val f = (s: String, i: Int) => s + i

  val g = f.mycurry

  println(g("herbert")(23))
  println(f.mycurry.myuncurry("hubert", 666))
}
