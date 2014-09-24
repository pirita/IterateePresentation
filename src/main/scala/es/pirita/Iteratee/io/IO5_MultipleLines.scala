package es.pirita.Iteratee.io

/**
  * IO
  *
  * @author Ignacio Navarro Martín
  * @version 1.0
  */
trait IO5_MultipleLines extends IO {

   override def numberWords(path: String): Int =
   try{
     val it = io.Source.fromFile(path).getLines()

     it.map( line => line.trim.split(" ").length + 1).reduce(_+_)
   } catch {
     case t: Throwable => -1
   }

}


