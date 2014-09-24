package es.pirita.Iteratee.io

/**
  * IO
  *
  * @author Ignacio Navarro Martín
  * @version 1.0
  */
trait IO3_Functional extends IO {

   override def numberWords(path: String): Int = {
     val it = io.Source.fromFile(path).getLines()
     it.map( line => line.trim.split(" ").length + 1).reduce(_+_)
   }

}


