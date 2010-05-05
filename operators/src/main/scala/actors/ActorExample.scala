package actors

import scala.actors.Actor
import scala.actors.Actor._

abstract class Operation
case class Add(i: Int, y: Int) extends Operation
case class Sub(i: Int, y: Int) extends Operation

object ActorExample {
  val producer: Actor = actor {
    loop {
      receive {
        case Add(x, y) => println(x + y)
        case Sub(x, y) => println(x - y)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    producer ! Add(3, 6)
    producer ! Sub(5, 2)
  }
}
