package es.pirita.Iteratee

import play.api.libs.iteratee._

import scala.concurrent.Await

import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Example with Iteratee
 *
 * @author Ignacio Navarro Martín
 * @version 1.0
 */




object Iteratee_3_Odd extends App
{

  val enum = Enumerator.enumerate(List.range(1, 10))

  def odd[E]: Iteratee[E, List[E]] = {
    def step(s: List[Option[E]])(i: Input[E]): Iteratee[E, List[E]] = i match {

      case Input.EOF => Done(s.flatten , Input.EOF)
      case Input.Empty => Cont[E, List[E]](i => step(s)(i))
      case Input.El(e) =>
        val el = if (s.last.isEmpty) Some(e) else Option.empty[E]
        Cont[E, List[E]](i => step( s :+ el )(i))

    }
    Cont[E, List[E]](i => step(List(Option.empty[E]))(i))
  }

  val futExample = enum |>>> odd

  println(Await.result(futExample, 10 seconds))
}


