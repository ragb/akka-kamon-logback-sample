aspectjSettings


name := "akka-kamon-logback-sample"

organization := "co.enear"

version := "0.1"

scalaVersion := "2.11.7"

  scalacOptions in Compile ++= Seq(
    "-encoding", "UTF-8",
    "-deprecation",
    "-unchecked",
    "-feature",
    "-Xlint",
    "-Ywarn-unused-import",
    "-language:implicitConversions",
    "-language:postfixOps"
  )


libraryDependencies ++= Seq(
"com.typesafe.akka" %% "akka-actor" % akkaVersion,
"com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
"io.kamon" %% "kamon-core" % kamonVersion,
"io.kamon" %% "kamon-akka" % kamonVersion,
"ch.qos.logback"          %  "logback-classic"          % "1.1.3"
)


  javaOptions in run <++= AspectjKeys.weaverOptions in Aspectj

fork in run := true

val akkaVersion = "2.3.14"
val kamonVersion = "0.5.2"


