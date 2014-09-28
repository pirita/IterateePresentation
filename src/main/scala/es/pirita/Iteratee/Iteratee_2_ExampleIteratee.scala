package es.pirita.Iteratee

import java.io.File

import play.api.libs.iteratee._

import scala.concurrent.{Future, Await}

import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Example with Iteratee
 *
 * @author Ignacio Navarro Martín
 * @version 1.0
 */


object Iteratee_2_ExampleIteratee extends App {

  val enumText: Enumerator[String] = Enumerator("A B C D E F G H I J K L M", "B 1 2 3 4 723 3", "SW, WW ZM", "X")

  val enumFile: Enumerator[String] = Enumerator.enumerate(scala.io.Source.fromFile(getClass.getClassLoader.getResource("aux.txt").getFile).getLines())

  val countWords: Iteratee[String, Int] = {
    def step(acc: Int)(i: Input[String]): Iteratee[String, Int] = i match {

      case Input.EOF => Done(acc, Input.EOF)
      case Input.Empty => Cont[String, Int](i => step(acc)(i))
      case Input.El(e) => Cont[String, Int](line => step(e.trim.split(" ").length + 1 + acc)(line))

    }
    Cont[String, Int](i => step(0)(i))
  }

  val futureEnumText: Future[Int] = enumText |>>> countWords
  assert(Await.result(futureEnumText, 10 seconds) ==  28)


  //Fold example
  val foldCountWords: Iteratee[String, Int] = Iteratee.fold[String, Int](0){ (acc, el) =>
    acc + el.trim.split(" ").length + 1
  }

  val futureFoldEnumText: Future[Int] = enumFile |>>> foldCountWords
  assert(Await.result(futureFoldEnumText, 10 seconds) ==  248)

  //Enumerator from File How???
  val enumFileApi: Enumerator[Array[Byte]] = Enumerator.fromFile(new File(getClass.getClassLoader.getResource("aux.txt").getFile))

}


