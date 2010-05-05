package xml


import java.util.Date
import java.text.DateFormat

object XmlExample {
  def main(args : Array[String]) : Unit = {
    
    val df = DateFormat.getDateInstance()
    val dateString = df format new Date()
    
    def theDate(name: String) = 
    	<dateMsg addressedTo={name}>
    		Hello, {name} ! Today is {dateString}
    	</dateMsg>;
    println(theDate("FooBar 2000") toString)
  }
}
