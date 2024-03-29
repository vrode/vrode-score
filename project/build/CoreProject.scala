import sbt._

class CoreProject( info: ProjectInfo ) extends DefaultProject( info ) {
    
    
    val mysql       =   "mysql" % "mysql-connector-java" % "5.1.18"
    val squeryl     =   "org.squeryl" %% "squeryl" % "0.9.4"
    val scalatest   =   "org.scalatest" %% "scalatest" % "1.6.1" % "test"
    val asmAll      =   "asm" % "asm-all" % "2.2"
    val scalaSwing  =   "org.scala-lang" % "scala-swing" % "2.9.1"
    val posgres     =   "postgresql" % "postgresql" % "8.4-701.jdbc4"

    
}