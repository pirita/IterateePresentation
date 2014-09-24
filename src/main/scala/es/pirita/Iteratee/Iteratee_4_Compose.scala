package es.pirita.Iteratee

import play.api.libs.iteratee._

import scala.concurrent.Await

import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Composing Iteratee
 *
 * @author Ignacio Navarro Martín
 * @version 1.0
 */




object Iteratee_4_Compose extends App
{

  val enum = Enumerator("Header", "Data", "Really", "Important")

  val dropHeaderConcatenate = for{
    _ <- Iteratee.head[String]
    acc <- Iteratee.fold[String, String]("")(_+_)
  } yield acc

  val futHead = enum |>>> dropHeaderConcatenate

  println(Await.result(futHead, 10 seconds))
}


