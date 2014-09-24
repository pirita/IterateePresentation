name := "Iteratee"

version := "1.0"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "play" %% "play-iteratees" % "2.1.1"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.0"

libraryDependencies += "org.scalaz" %% "scalaz-iteratee" % "7.1.0"