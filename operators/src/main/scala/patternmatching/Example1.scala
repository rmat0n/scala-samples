package xke.example

import scala.BigDecimal

abstract class Property {
  val value: BigDecimal
}
case class House(address: Address, override val value: BigDecimal) extends Property
case class Car(horsepower: Int, override val value: BigDecimal) extends Property
case class Horse(Age: Int, override val value: BigDecimal) extends Property
case class Address(street: String, city: String)

object Example {
  def applyPropertyValue(property: Property): BigDecimal = property match {
    case house: House => house.value * 0.8
    case horse: Horse => horse.value * 0.5
    case car: Car => 0
    case _ => property.value
  }

  def main(args: Array[String]): Unit = {
    println(applyPropertyValue(new Horse(10, 30000)))
  }
}
