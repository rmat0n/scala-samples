import sbt._
import de.element34.sbteclipsify._

class MainProject(info: ProjectInfo) extends ParentProject(info) with IdeaPlugin {
  lazy val operatorsProject = project("operators", "Operators", new OperatorsProject(_) with IdeaPlugin with Eclipsify)
  
  class OperatorsProject(info: ProjectInfo) extends DefaultProject(info) {
    val jodatime = "joda-time" % "joda-time" % "1.6"
    val scalatest = "org.scalatest" % "scalatest" % "1.0" % "test"
    val scalaToolsRepo = "ScalaTools Repository" at "http://scala-tools.org/repo-releases/"
  }
}