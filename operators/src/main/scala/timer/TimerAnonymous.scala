package timer


object TimerAnonymous {
  def main(args : Array[String]) : Unit = {
    oncePerSecond(() => println("My anonymous text"))
  }
  def oncePerSecond(callback: () => Unit) {
	while(true) { callback(); Thread sleep 1000 }
  }
}
