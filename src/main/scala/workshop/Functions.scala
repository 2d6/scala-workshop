package workshop

/**
  * In this set of exercises, we will implement a few operations on functions to familiarize ourselves with treating
  * functions as first class citizens.
  */
object Functions {

  /**
    * Write a function 'compose', which takes two functions (f: A => B and g: B => C) as arguments and returns a new
    * function with the signature A => C. This means that the returned function h should be the composition of f and g:
    * h(x) := g(f(x))
    */
  def compose[A, B, C](f: A => B, g: B => C): A => C = ???

  /**
    * Write a function 'partial', which converts a bifunction (A, B) => C to a function only taking one argument
    * (A => C) by fixing the parameter of type B to a constant value
    */
  def partial[A, B, C](f: (A, B) => C, b: B): A => C = ???

  /*
  TDD exercise:
  Write a function 'curry', which converts a function with two arguments into a function with two argument sets.

  Hint: A function with two argument sets is the same as a function yielding another function as a result.
  Example: the signature of following function:
  def f[A,B,C](a: A)(b: B): C
  could also be expressed by:
  val f: A => (B => C)
  This means that you can e.g. only supply the first set of parameters, resulting in something similar to partial
  application.
   */
  def curry[A,B,C](f: (A, B) => C): A => (B => C) = ???

  /*
  TDD exercise:
  Write a function 'uncurry', which acts as the inverse of 'curry'
   */

  /*
  Exercise:
  As you have seen, Scala functions may possess more than one set of parameters. The last set of parameters may be
  marked 'implicit' like this:
  def prefixString(number: Int)(implicit prefix: String): String = prefix + number

  This allows us to omit the implicit set of parameters, if the compiler finds a value marked 'implicit' in scope:
  implicit val prefix: String = "number: "
  prefixString(5) // prints out 'number: 5'
  prefixString(5)("otherNumber") // prints out 'otherNumber: 5'

  These implicit parameters are often context objects that would otherwise have to be explicitly handed to the function,
  cluttering the code. In the following example, we will use this pattern to supply a logger to a function. An example
  logger implementation is given below.

  Write a function sum(s: Seq[Int]) which calculates the sum of the integers in a given Seq. The function should also
  accept an implicit Logger and use it to print out the sum at each step. Thus, the call to sum(3) should result in
  the output
  [INFO] current sum: 1
  [INFO] current sum: 2
  [INFO] current sum: 3
   */

  /*
  Exercise:
  Try defining a second implicit logger in the same scope as the previous exercise. What happens when running the code
  (and why)?
   */

  class Logger {
    def info(message: String): Unit = println(s"[INFO] $message")
  }
}
