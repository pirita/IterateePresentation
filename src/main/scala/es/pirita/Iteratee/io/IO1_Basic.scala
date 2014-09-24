package es.pirita.Iteratee.io


/**
  * IO
  *
  * @author Ignacio Navarro Martín
  * @version 1.0
  */
trait IO1_Basic extends IO {

   override def numberWords(path: String): Int = {
     val it = io.Source.fromFile(path).getLines()
     var acc = 0

     while( it.hasNext ) {
       val line = it.next
       acc = acc + line.trim.split(" ").length + 1
     }
     acc
   }

}

