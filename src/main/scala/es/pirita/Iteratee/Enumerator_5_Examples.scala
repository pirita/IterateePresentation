package es.pirita.Iteratee

import play.api.libs.iteratee.{Iteratee, Enumerator}

import scala.concurrent.{Future, Await}

import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Enumerator Examples
 *
 * @author Ignacio Navarro Martín
 * @version 1.0
 */




object Enumerator_5_Examples extends App
{

    val enum: Enumerator[Int] = Enumerator.enumerate(Stream.from(1, 2))

    def plusN(n: Int): Iteratee[Int, Int] = Iteratee.fold2(0){ (ac, ele) =>
      Future successful(  (ac + ele, ele >= n) )
    }

    val futExample: Future[Int]  = enum |>>> plusN(1000)
    assert(Await.result(futExample, 10 seconds) == 251001)

}


