package xke.example

import java.sql._
import java.net._

object ExceptionHandling {
	def main(args : Array[String]) : Unit = {
		var conn:Connection = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "sa", "")
		try {
			var stmt:PreparedStatement = conn prepareStatement "INSERT INTO xebians (xebia-boy) VALUES (?)"
			stmt.setObject(1, new URL("http://blog.xebia.fr"))
			stmt.executeUpdate()
			stmt.close()
		} catch {
		  	case e: SQLException => println("Database error")
		  	case e: MalformedURLException => println("Bad URL")
		  	case e => {
		  		println("Some other exception type : ")
		  		e.printStackTrace()
		  	}
		} finally {
			conn.close()
		}
	}
}
