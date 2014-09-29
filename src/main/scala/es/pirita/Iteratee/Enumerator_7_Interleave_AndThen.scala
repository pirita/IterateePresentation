package es.pirita.Iteratee


import play.api.libs.iteratee.{Iteratee, Enumerator}

import scala.concurrent.{Future, Await}

import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Enumerator Interleave
 *
 * @author Ignacio Navarro Martín
 * @version 1.0
 */




object Enumerator_7_Interleave_AndThen extends App
{

  def createEnumerator[T](enum: Iterator[T], delay: Int): Enumerator[T] = {
    Enumerator.generateM{
      Future{
        Thread.sleep(1000)
        if (enum.hasNext) Option(enum.next) else Option.empty[T]
      }
    }
  }

  val enumI: Enumerator[String] = createEnumerator(List.range(1, 5).map(_.toString).iterator, 1000)
  val enumS: Enumerator[String] = createEnumerator(List("☂", "❄", "☎", "T").iterator, 500)

  val fAndThen: Future[Unit] = enumI >>> enumS |>>> Iteratee.foreach(x => println(s"$x "))
  val fInterleave: Future[Unit] = enumI >- enumS |>>> Iteratee.foreach(x => println(s"$x "))

  Await.ready(fAndThen, Duration.Inf)
  Await.ready(fInterleave, Duration.Inf)
}


