package es.pirita.Iteratee

import es.pirita.Iteratee.io._

/**
 * Example with IO
 *
 * @author Ignacio Navarro Martín
 * @version 1.0
 */

object Iteratee_1_IO extends App with IO{
  override def numberWords(path: String): Int = ???

  println(numberWords("aux.txt"))
}

