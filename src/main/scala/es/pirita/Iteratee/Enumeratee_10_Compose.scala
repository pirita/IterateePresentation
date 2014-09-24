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




object Enumeratee_10_Compose extends App
{
  val enum: Enumerator[Int]  = Enumerator.enumerate(List.range(1, 10))

  //Iteratee Float
  val iteratee01: Iteratee[String, Float] = Iteratee.fold[String, Float](0f){ (acc, el) =>
    acc + el.toFloat + 0.1f
  }

  val enumeratee = Enumeratee.map[Int]{s => s.toString}

  //--------
  //We can change the enumerator
  //--------
  val newEnumerator: Enumerator[String] = enum &> enumeratee
  val futExampleEnumerator = newEnumerator |>>> iteratee01
  println(Await.result(futExampleEnumerator, 10 seconds))


  //--------
  //We can apply it to an Iteratee
  //--------
  val newIteratee: Iteratee[Int, Float] = enumeratee &>> iteratee01
  val futExampleIteratee = enum |>>> newIteratee
  println(Await.result(futExampleIteratee, 10 seconds))




  //--------
  //Compose two enumeratee
  //--------
  val enumCompose: Enumerator[String]  = Enumerator("1", "2", "3", "4", "5")
  //Iteratee Float
  val iterateeCompose: Iteratee[Int, Int] = Iteratee.fold[Int, Int](0){ (acc, el) =>
    acc + el
  }
  val enumerateeFilter = Enumeratee.filter[Int]( x => x % 2 == 0)
  val enumerateeInt = Enumeratee.map[String](x => x.toInt)
  val enumerateeCompose = enumerateeInt ><> enumerateeFilter

  val futEnumerateeCompose = enumCompose &> enumerateeCompose |>>> newIteratee
  println(Await.result(futEnumerateeCompose, 10 seconds))

  //Differences betwee ><> and >+>
  //><> Drops after Done
  //>+> Concants Done

  //Recordar que puedes usar apply y flatten en un iteratee y devolverlo a su estado original
}


