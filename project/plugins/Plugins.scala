import sbt._
class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  lazy val repo = "GH-pages repo" at "http://mpeltonen.github.com/maven/"
  lazy val idea = "com.github.mpeltonen" % "sbt-idea-plugin" % "0.1-SNAPSHOT"
  lazy val eclipse = "de.element34" % "sbt-eclipsify" % "0.5.2-SNAPSHOT"
}