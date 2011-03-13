import sbt._
import sbt.BasicScalaProject._

class SubmititProject(info: ProjectInfo) extends DefaultProject(info){
  
    val mockito = "org.mockito" % "mockito-all" % "1.8.5"

}