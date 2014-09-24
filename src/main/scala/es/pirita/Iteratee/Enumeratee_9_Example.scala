package es.pirita.Iteratee

import play.api.libs.iteratee.{Enumeratee, Iteratee, Enumerator}

import scala.concurrent.{Future, Await}

import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Enumeratee Example
 *
 * @author Ignacio Navarro Martín
 * @version 1.0
 */




object Enumeratee_9_Example extends App
{
  val enum: Enumerator[Int]  = Enumerator.enumerate(List.range(1, 10))

  //Iteratee Float
  val iteratee: Iteratee[String, Float] = Iteratee.fold[String, Float](0f){ (acc, el) =>
    acc + el.toFloat + 0.1f
  }


  //We can change the enumerator
  val enumeratee: Enumeratee[Int, String] = Enumeratee.map[Int]{s => s.toString}
  val enumerator: Enumerator[String] = enum &> enumeratee

  val futExampleEnumerator = enumerator |>>> iteratee
  println(Await.result(futExampleEnumerator, 10 seconds))

}


