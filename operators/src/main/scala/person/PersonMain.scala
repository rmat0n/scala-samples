package person

class Person1 {
	var age:Int = _
	def isOverAge = age > 18
}

class Person2(age:Int) {
	def isOverAge = age > 18
}

class Person3(var age:Int) {
	def isOverAge = age > 18
}

class Person4(private var age:Int) {
	def isOverAge = age > 18
	def grow = age += 1
	def getAge = age
}

object PersonMain {
	def main(args : Array[String]) : Unit = {
		var p1 = new Person1
		println(p1 age)
		println(p1 isOverAge)
		p1 age = 26
		println(p1 age)
		var p2 = new Person2(24)
		println(p2 isOverAge)
		var p3 = new Person3(17)
		println(p3 age)
		println(p3 isOverAge)
		var p4 = new Person4(17)
		println(p4 isOverAge)
		println(p4 getAge)
		println(p4 grow)
		println(p4 isOverAge)
	}
 
}
