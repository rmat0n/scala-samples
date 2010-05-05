package genericity

class Reference[T] {
  private var contents: T = _  
  def set(value: T) {contents = value}
  def get: T = contents
}

object Genericity {
  def main(args: Array[String]): Unit = {
    // Default value
    var cell = new Reference[Int]
    println("Reference contains the half of " + cell.get * 2)
    // Set a value
    cell = new Reference[Int]
    cell.set(13)
    println("Reference contains the half of " + cell.get * 2)
    // Another type
    var cell2 = new Reference[String]
    cell2.set("FooBar")
    println("Reference value is " + cell2.get)
  }
}
