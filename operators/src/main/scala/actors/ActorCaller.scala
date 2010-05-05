package actors


object ActorCaller {
	def main(args: Array[String]): Unit = {
    print("Go")
		ActorExample.producer ! Add(20,30)
		exit
	}
}
