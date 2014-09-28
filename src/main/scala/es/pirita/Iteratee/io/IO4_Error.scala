package es.pirita.Iteratee.io

/**
  * IO
  *
  * @author Ignacio Navarro Martín
  * @version 1.0
  */
trait IO4_Error extends IO {

   override def numberWords(path: String): Int =
   try{
     val it: Iterator[String] = io.Source.fromFile(path).getLines()
     it.map( line =>
       line.trim.split(" ").length + 1).
       fold(0)((acc: Int, el: Int) => acc + el)
   } catch {
     case t: Throwable => 0
   }

}


