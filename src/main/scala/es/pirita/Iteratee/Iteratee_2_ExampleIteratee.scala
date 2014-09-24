package es.pirita.Iteratee

import play.api.libs.iteratee.{Iteratee, Enumerator}

import scala.concurrent.Await

import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Example with Iteratee
 *
 * @author Ignacio Navarro Martín
 * @version 1.0
 */




object Iteratee_2_ExampleIteratee extends App
{

  val enum = Enumerator.enumerate(List.range(1, 10))

  //Iteratee 1 Sum all the even elements
  val iterateeSumEvenNumbers = Iteratee.fold[Int, Int](0){ (acc, el) =>
    if (el % 2 == 0) acc + el else acc
  }

  val futExample = enum |>>> iterateeSumEvenNumbers

  //Whenever we want, we can wait for the result
  println(Await.result(futExample, 10 seconds))
}


