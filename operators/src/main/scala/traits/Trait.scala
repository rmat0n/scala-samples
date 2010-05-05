package traits

trait Ord {
  def < (that: Any): Boolean
  def <=(that: Any): Boolean = (this < that) || (this == that)
  def > (that: Any): Boolean = !(this <= that)
  def >=(that: Any): Boolean = !(this < that)
}

class Date(y: Int, m: Int, d: Int) extends Ord {
  def year = y
  def month = m
  def day = d
  override def toString = year + "-" + (if(month < 10) "0" else "") + month + "-" + (if(day < 10) "0" else "") + day
  override def equals(that: Any): Boolean = that.isInstanceOf[Date] && {
    val thatDate = that.asInstanceOf[Date]
    thatDate.year == this.year && thatDate.month == this.month && thatDate.day == this.day
  }
  def < (that: Any): Boolean = {
    if(!that.isInstanceOf[Date])
      error("Cannot compare " + that + " and a Date")
    val thatDate = that.asInstanceOf[Date]
    (thatDate.year < this.year) || 
      (thatDate.year == this.year && (thatDate.month < this.month || 
                                        (thatDate.month == this.month && thatDate.day < this.day)))
  }
}

object Trait {
  def main(args : Array[String]) : Unit = {
    println(new Date(2009,7,9))
    println(new Date(2009,10,9))
    println(new Date(2009,2,21))
    println(new Date(2009,12,25))
    val date1 = new Date(2009,12,25)
    println(date1 + " equals " + date1 + " = " + date1.equals(date1))
    val date2 = new Date(2009,2,3)
    println(date1 + " equals " + date2 + " = " + date1.equals(date2))
    println(date1 + " < " + date1 + " = " + (date1<(date1)))
    println(date1 + " < " + date2 + " = " + (date1<(date2)))
    println(date1 + " < " + "foobar" + " = " + (date1<("foobar")))
  }
}
