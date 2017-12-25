
object Compose extends App {

  def compose[A, B, C](f: A => B, g: B => C): A => C = {
    i => g(f(i))
  }

  val f1: Int => String = "string" + _
  val f2: String => Option[String] = Some(_)

  val g1 = compose(f1, f2)

  println(g1(23))
  println(f2.compose(f1)(666))
}
