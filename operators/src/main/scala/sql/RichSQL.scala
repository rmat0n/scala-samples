package sql

// In Scala we can specify multiple imports from the same package
// by combining them together. 
import java.sql.{DriverManager, Connection, ResultSet, PreparedStatement, Statement, Date }
import org.joda.time.DateTime;
// We can also rename an import to avoid a name collision. Here
// we're changing the name of java.util.Random to JRandom.
import java.util.{Random => JRandom};
import DriverManager.{getConnection => connect};
 
// The underscore is used to import everything in a package or object.
// "Console" is a Scala singleton object; we are bringing its public
// declarations into our namespace. Note that when we do this we are
// bringing in types, methods, classes -- everything. 
import Console._;
 
/** TestF2 demonstrates the use of the RichSQL object's facilities
to make JDBC work easier than Java. */
import java.sql.{DriverManager, Connection, ResultSet, PreparedStatement, Statement, Date };
object RichSQL {
private def strm[X](f: RichResultSet => X, rs: ResultSet): Stream[X] = 
        if (rs.next) Stream.cons(f(new RichResultSet(rs)), strm(f, rs))
        else { rs.close(); Stream.empty };
 
    implicit def query[X](s: String, f: RichResultSet => X)(implicit stat:Statement) = {
        strm(f,stat.executeQuery(s));
    }
    
    implicit def conn2Statement(conn: Connection): Statement = conn.createStatement;
    
    implicit def rrs2Boolean(rs: RichResultSet) = rs.nextBoolean;
    implicit def rrs2Byte(rs: RichResultSet) = rs.nextByte;
    implicit def rrs2Int(rs: RichResultSet) = rs.nextInt;
    implicit def rrs2Long(rs: RichResultSet) = rs.nextLong;
    implicit def rrs2Float(rs: RichResultSet) = rs.nextFloat;
    implicit def rrs2Double(rs: RichResultSet) = rs.nextDouble;
    implicit def rrs2String(rs: RichResultSet) = rs.nextString;
    implicit def rrs2Date(rs: RichResultSet) = rs.nextDate;
    implicit def rrs2DateTime(rs: RichResultSet):DateTime = new DateTime(rs.nextDate.getTime);
 
    implicit def resultSet2Rich(rs: ResultSet) = new RichResultSet(rs);
    implicit def rich2ResultSet(r: RichResultSet) = r.rs;
    class RichResultSet(val rs: ResultSet) {
        
      var pos = 1
      def apply(i: Int) = { pos = i; this }
        
      def nextBoolean: Boolean = { val ret = rs.getBoolean(pos); pos = pos + 1; ret }
      def nextByte: Byte = { val ret = rs.getByte(pos); pos = pos + 1; ret }
      def nextInt: Int = { val ret = rs.getInt(pos); pos = pos + 1; ret }
      def nextLong: Long = { val ret = rs.getLong(pos); pos = pos + 1; ret }
      def nextFloat: Float = { val ret = rs.getFloat(pos); pos = pos + 1; ret }
      def nextDouble: Double = { val ret = rs.getDouble(pos); pos = pos + 1; ret }
      def nextString: String = { val ret = rs.getString(pos); pos = pos + 1; ret }
      def nextDate: Date = { val ret = rs.getDate(pos); pos = pos + 1; ret }
 
      def foldLeft[X](init: X)(f: (ResultSet, X) => X): X = rs.next match {
        case false => init
        case true => foldLeft(f(rs, init))(f)
      }
      def map[X](f: ResultSet => X) = {
        var ret = List[X]()
        while (rs.next())
        ret = f(rs) :: ret
        ret.reverse; // ret should be in the same order as the ResultSet
      }
    }
 
   
    
    implicit def ps2Rich(ps: PreparedStatement) = new RichPreparedStatement(ps);
    implicit def str2RichPrepared(s: String)(implicit conn: Connection): RichPreparedStatement = conn prepareStatement(s);
    implicit def rich2PS(r: RichPreparedStatement) = r.ps;
 
    class RichPreparedStatement(val ps: PreparedStatement) {
      var pos = 1;
      private def inc = { pos = pos + 1; this }
 
      def execute[X](f: RichResultSet => X): Stream[X] = {
	pos = 1; strm(f, ps.executeQuery)
      }
      def <<![X](f: RichResultSet => X): Stream[X] = execute(f);
 
      def execute = { pos = 1; ps.execute }
      def <<! = execute;
 
      def <<(b: Boolean) = { ps.setBoolean(pos, b); inc }
      def <<(x: Byte) = { ps.setByte(pos, x); inc }
      def <<(i: Int) = { ps.setInt(pos, i); inc }
      def <<(x: Long) = { ps.setLong(pos, x); inc }
      def <<(f: Float) = { ps.setFloat(pos, f); inc }
      def <<(d: Double) = { ps.setDouble(pos, d); inc }      
      def <<(o: String) = { ps.setString(pos, o); inc }
      def <<(x: Date) = { ps.setDate(pos, x); inc }
    }
    
    implicit def conn2Rich(conn: Connection) = new RichConnection(conn);
    
    class RichConnection(val conn: Connection) {
      def <<(sql: String) = new RichStatement(conn.createStatement) << sql;
      def <<(sql: Seq[String]) = new RichStatement(conn.createStatement) << sql;
    }
 
    implicit def st2Rich(s: Statement) = new RichStatement(s);
    implicit def rich2St(rs: RichStatement) = rs.s;
    
    class RichStatement(val s: Statement) {
      def <<(sql: String) = { s.execute(sql); this }
      def <<(sql: Seq[String]) = { for (val x <- sql) s.execute(x); this }
    }
}
