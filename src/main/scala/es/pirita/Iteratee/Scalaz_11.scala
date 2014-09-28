package es.pirita.Iteratee



import scalaz._, Scalaz._, iteratee._, Iteratee._


/**
 * Scalaz
 *
 * @author Ignacio Navarro Martín
 * @version 1.0
 */




object Scalaz_11 extends App
{
  def counter[E]: Iteratee[E, Int] = {
    def step(acc: Int)(s: Input[E]): Iteratee[E, Int] =
      s(el = e => cont(step(acc + 1)),
        empty = cont(step(acc)),
        eof = done(acc, eofInput[E])
      )
    cont(step(0))
  }

  val countElements: Iteratee[Int, Int] = counter[Int] &= enumerate(Stream(1, 2, 3))

  assert(countElements.run ==  3)

}


