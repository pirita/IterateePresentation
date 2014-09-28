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




object Enumerator_8_Compose extends App
{

  val dataInt: Iterator[String] = List.range(1, 5).map(_.toString).iterator
  val dataSt: Iterator[String] = List("☂", "❄", "☎", "T").iterator

  val enumI: Enumerator[String] = Enumerator.generateM{
    Future{
      Thread.sleep(1000)
      if (dataInt.hasNext) Option(dataInt.next) else Option.empty[String]
    }
  }

  val enumS: Enumerator[String] = Enumerator.generateM{
    Future{
      Thread.sleep(500)
      if (dataSt.hasNext) Option(dataSt.next) else Option.empty[String]
    }
  }

  val enumCompose: Enumerator[String] = for {
    a <- enumI
    b <- enumS
  } yield a + b

  val fCompose: Future[Unit] = enumCompose |>>> Iteratee.foreach(x => println(s"$x "))

  Await.ready(enumCompose |>>> Iteratee.foreach(x => println(s"$x ")), 10 seconds)
}


