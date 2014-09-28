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

  val enumeratee: Enumeratee[Int, String] = Enumeratee.map[Int]{s => s.toString}

  //--------
  //We can change the enumerator
  //--------
  val newEnumerator: Enumerator[String] = enum &> enumeratee
  val futExampleEnumerator: Future[Float] = newEnumerator |>>> iteratee01
  assert(Await.result(futExampleEnumerator, 10 seconds) == 45.899998f)


  //--------
  //We can apply it to an Iteratee
  //--------
  val newIteratee: Iteratee[Int, Float] = enumeratee &>> iteratee01
  val futExampleIteratee: Future[Float] = enum |>>> newIteratee
  assert(Await.result(futExampleIteratee, 10 seconds) == 0.0f)




  //--------
  //Compose two enumeratee
  //--------
  val enumCompose: Enumerator[String]  = Enumerator("1", "2", "3", "4", "5")
  //Iteratee Float
  val iterateeCompose: Iteratee[Int, Int] = Iteratee.fold[Int, Int](0){ (acc, el) =>
    acc + el
  }
  val enumerateeFilter: Enumeratee[Int, Int] = Enumeratee.filter[Int]( x => x % 2 == 0)
  val enumerateeInt: Enumeratee[String, Int] = Enumeratee.map[String](x => x.toInt)
  val enumerateeCompose: Enumeratee[String, Int] = enumerateeInt ><> enumerateeFilter

  val futEnumerateeCompose: Future[Float] = enumCompose &> enumerateeCompose |>>> newIteratee
  assert(Await.result(futEnumerateeCompose, 10 seconds) == 6.2f)

  //Differences betwee ><> and >+>
  //><> Drops after Done
  //>+> Concants Done

  //Recordar que puedes usar apply y flatten en un iteratee y devolverlo a su estado original
}


