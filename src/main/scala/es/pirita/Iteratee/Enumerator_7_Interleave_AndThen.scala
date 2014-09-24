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

  val dataInt = List.range(1, 5).map(_.toString).iterator
  val dataSt = List("☂", "❄", "☎", "T").iterator

  val enumI = Enumerator.generateM{
    Future{
      Thread.sleep(1000)
      if (dataInt.hasNext) Option(dataInt.next) else Option.empty[String]
    }
  }

  val enumS = Enumerator.generateM{
    Future{
      Thread.sleep(500)
      if (dataSt.hasNext) Option(dataSt.next) else Option.empty[String]
    }
  }

  val fAndThen = enumI >>> enumS |>>> Iteratee.foreach(x => println(s"$x "))
  val fInterleave = enumI >- enumS |>>> Iteratee.foreach(x => println(s"$x "))

  Await.ready(fAndThen, Duration.Inf)
  Await.ready(fInterleave, Duration.Inf)
}

