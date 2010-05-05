package helloworld

import java.text.DateFormat._
import java.util.{List, ArrayList, Date, Locale}

object HelloWorld {
  def main(args : Array[String]) {
    // Helloworld
    println("Hello World !")

    // Format now date
    val now = new Date
    val df = getDateInstance(LONG, Locale.FRANCE)
    println(df format now)

    // list
    var list = scala.List(1,2,3);
    println(list.mkString(","))

    var notTypedList = new ArrayList[Int]();
    notTypedList.trimToSize();
    var typedList:List[Int] = new ArrayList[Int]();
    // method does not exist : typedList.trimToSize();

    var getInt = (option:Option[Int]) => option match {
      case Some(x) => "Value is "+x
      case None => "No value..."
    }
    println(getInt(Some(42)))
    println(getInt(None))
  }
}
