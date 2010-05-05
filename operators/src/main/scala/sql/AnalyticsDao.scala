package sql

import java.sql.{DriverManager, Statement, Date}
import org.joda.time.DateTime;
import DriverManager.{getConnection => connect};
import Console._;

object Conf {
  def dbConnectionString = ""
  def dbUsername = ""
  def dbPassword = ""
}

object AnalyticsDao {
  import RichSQL._;
  implicit def date2JodaTime(source: Date): DateTime = {
    new DateTime(source.getTime)
  }

  implicit def jodaTime2Date(source: DateTime): Date = {
    new Date(source.toDate.getTime)
  }

  def newConnection = connect(Conf.dbConnectionString, Conf.dbUsername, Conf.dbPassword)

  val h2driver = Class.forName("org.gjt.mm.mysql.Driver");

  private def requestsFor(userId: String): String = "select * from analytics where User_Id='" + userId + "'"

  def getRequestsFor(userId: String) = {
    implicit val conn = newConnection
    implicit val s: Statement = conn;
    val request = conn prepareStatement "select * from analytics where User_Id=?"
    request << userId <<! (rs => Request1(rs, rs, rs))
  }

  def getAllRequests() = {
    implicit val conn = newConnection
    implicit val s: Statement = conn;
    query("select * from analyticsResults", rs => Request1(rs, rs, rs))
  }

  def getAllRequestsOfUserAndMonth(userId: String, month: DateTime): List[Request1] = {
    implicit val conn = newConnection
    implicit val s: Statement = conn;
    val request = conn prepareStatement "select * from analytics where User_Id=? and month(Request_Date)= month(?) and year(Request_Date)= year(?)"
    val date = new Date(month.getTime())
    (request << userId << date << date <<! (rs => Request1(rs, rs, rs))).toList
  }

  def findHitsPerDayBetween(apiKey: String, between: (DateTime, DateTime)): List[Request1] = {
    implicit val conn = newConnection
    implicit val s: Statement = conn;
    val request = conn prepareStatement "select * from analytics where User_Id=? and Request_Date BETWEEN ? and ?"
    (request << apiKey << between._1 << between._2 <<! (rs => Request1(rs, rs, rs))).toList
  }

  def getAllRequestsOfUserAndYear(userId: String, year: DateTime): List[Request1] = {
    implicit val conn = newConnection
    implicit val s: Statement = conn;
    val request = conn prepareStatement "select * from analytics where User_Id=? and year(Request_Date)= year(?)"
    val date = new Date(year.getTime())
    (request << userId << date <<! (rs => Request1(rs, rs, rs))).toList
  }

  def countRequestForDay(userId: String, day: java.util.Date): Int = {
    implicit val conn = newConnection
    implicit val s: Statement = conn;
    val request = conn prepareStatement "select count(*) from analytics where User_Id=? and day(Request_Date)= day(?) and month(Request_Date)= month(?) and year(Request_Date)= year(?)"
    val date = new Date(day.getTime())
    ((request << userId << date << date << date) <<! (rs => rs)).head
  }

  def countRequestForMonth(userId: String, day: java.util.Date): Int = {
    implicit val conn = newConnection
    implicit val s: Statement = conn;
    val request = conn prepareStatement "select count(*) from analytics where User_Id=? and month(Request_Date)= month(?) and year(Request_Date)= year(?)  "
    val date = new Date(day.getTime())
    ((request << userId << date << date) <<! (rs => rs)).head
  }

  def getRequestsFor1(userId: String) = {
    implicit val conn = newConnection
    requestsFor(userId) <<! (rs => Request1(rs, rs, rs))
  }

  def insertRequest(userId: String, requestDate: java.util.Date, url: String) {
    implicit val conn = newConnection
    val insertRequest = conn prepareStatement "insert into analytics(User_Id, Request_Date,Service_Url) values(?, ?, ?)";
    insertRequest << userId << new Date(requestDate.getTime()) << url <<!
  }

  case class Request1(userId: String, requestDate: DateTime, url: String)

  def main(args: Array[String]): Unit = {

    println(new Date(13, 1980, 3))
    //insertRequest("aaa",new java.util.Date(),"")
    //val requests= getRequestsFor("aaa")
    //for (rq<- requests) println((rq requestDate) + " "+ (rq userId))
    println(countRequestForDay("aaa", new java.util.Date()))
  }


}
