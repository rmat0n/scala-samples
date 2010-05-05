package oop


class Complex(real: Double, imaginary :Double) {
  def re = real
  def im = imaginary
  override def toString = "" + (if(real < 0) "" else "+") + real + (if(imaginary < 0) "" else "+") + imaginary + "i"
}

object Inheritance {
  def main(args : Array[String]) : Unit = {
    var c = new Complex(12d, 3d);
    println(c.toString + " for " + c.re + "@" + c.im);
    c = new Complex(-12d, 3d);
    println(c.toString + " for " + c.re + "@" + c.im);
    c = new Complex(12d, -3d);
    println(c.toString + " for " + c.re + "@" + c.im);
    c = new Complex(-12d, -3d);
    println(c.toString + " for " + c.re + "@" + c.im);
  }
}
