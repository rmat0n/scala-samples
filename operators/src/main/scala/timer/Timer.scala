package timer

object Timer {
  def main(args: Array[String]): Unit = {
    oncePerSecond(timeFlies)
  }
  def oncePerSecond(callback: () => Unit) {
    while (true) {callback(); Thread sleep 1000}
  }
  def timeFlies() {
    println("My text")
  }
}
