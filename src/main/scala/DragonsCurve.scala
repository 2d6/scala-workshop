
/**
  * Dragon's Curve Kata
  *
  * The Dragon's Curve is a fractal, the path of which can be easily determined programmatically.
  * *
  * Start with the string "Fa"
  * Replace all "a" with "aRbFR" and all "b" with "LFaLb"
  * *
  * That is (spaces added for clarity):
  * *
  * Iteration 0: Fa
  * Iteration 1: Fa -> F aRbFR
  * Iteration 2: FaRbFR -> F aRbFR R LFaLb FR
  * *
  * You will do this "n" times. Then, remove all "a" and "b". The remaining string will contain only FRL (the commands
  * go Forward, turn Right, and turn Left). On a grid, tracing the series of commands results in the Dragon's Curve.
  *
  * Implement the function 'create' in the DragonsCurve object to satisfy the unit test cases.
  */
object DragonsCurve {

  def create(iterations: Int): String = ???

}

