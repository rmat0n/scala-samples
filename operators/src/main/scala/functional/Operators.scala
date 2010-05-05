package functional

object Operators {
  def multiply(x:Int)(y:Int):Int = x*y
  def double = multiply(2)(_)
}