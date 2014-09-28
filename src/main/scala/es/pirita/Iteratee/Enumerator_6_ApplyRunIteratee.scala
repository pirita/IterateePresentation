package es.pirita.Iteratee

import play.api.libs.iteratee.{Iteratee, Enumerator}

import scala.concurrent.{Future, Await}

import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.global

/**
 * Example with Iteratee
 *
 * @author Ignacio Navarro Martín
 * @version 1.0
 */




object Enumerator_6_ApplyRunIteratee extends App
{

  val enum: Enumerator[Int] = Enumerator(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

  //Iteratee 1 Sum all the even elements
  val iterateeSumEvenNumbers: Iteratee[Int, Int] = Iteratee.fold[Int, Int](0){ (acc, el) =>
    if (el % 2 == 0) acc + el else acc
  }


  //Instead of run |>>>, we use apply |>> that returns a Promise[Iteratee[E|A]] instead of Promise[A]
  val lastStateIteratee: Iteratee[Int, Int] = Iteratee.flatten(enum |>> iterateeSumEvenNumbers)

  //We use the Iteratee in the last state with a new Enumerator
  val futexample: Future[Int] = enum |>>> lastStateIteratee


  //Whenever we want, we can wait for the result
  assert(Await.result(futexample, 10 seconds) == 60)
}


