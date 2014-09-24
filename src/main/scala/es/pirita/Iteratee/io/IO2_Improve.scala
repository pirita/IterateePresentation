package es.pirita.Iteratee.io

/**
  * IO
  *
  * @author Ignacio Navarro Martín
  * @version 1.0
  */
trait IO2_Improve extends IO {

   override def numberWords(path: String): Int = {
     val it = io.Source.fromFile(path).getLines()
     var acc = 0
     it foreach { line =>
       acc = acc + line.trim.split(" ").length + 1
     }
     acc
   }
}

