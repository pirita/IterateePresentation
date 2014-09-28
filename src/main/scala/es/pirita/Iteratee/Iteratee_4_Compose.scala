package es.pirita.Iteratee

import play.api.libs.iteratee._

import scala.concurrent.{Future, Await}

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

  val enum: Enumerator[String] = Enumerator("Header", "Data", "Really", "Important")

  val dropHeaderConcatenate: Iteratee[String, String] = for{
    _ <- Iteratee.head[String]
    acc <- Iteratee.fold[String, String]("")(_+_)
  } yield acc

  val futHead: Future[String] = enum |>>> dropHeaderConcatenate

  assert(Await.result(futHead, 10 seconds)=="DataReallyImportant")
}


