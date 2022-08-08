package io.univalence.education

import io.univalence.education.internal.exercise_tools.*
import io.univalence.education.internal.implicits.*

import scala.annotation.tailrec
import scala.util.Random

import java.time.LocalDate

/**
 * Scala comes with a standard library that implements commonly used
 * codes.
 *
 * One of the most important part of the standard library are their
 * immutable data structures. We can find them in the package
 * `scala.collection.immutable`.
 *
 * You will certainly use some of them during your Scala journey. Thus
 * it is interesting to practice a bit with them!
 */
@main
def standard_library(): Unit =
  /**
   * List[A] is by far the most important data structure available in
   * the Scala standard library. It represents a list of elements. It is
   * similar to a Linked List available in many other programming
   * languages.
   *
   * In Scala, this data structure comes with plenty of helping
   * functions which make your life easier.
   *
   * Let's see some of them.
   */
  section("Practicing with List[A]") {
    exercise("Create a list containing a 1") {
      val list: List[Int] = List(1)

      check(list.length == 1)
      check(list.headOption.contains(1))
    }

    val numbers: List[Int] = List(1, 2, 3, 4, 5)

    exercise("Find the size of a list") {
      val numberOfNumbers: Int = numbers.length

      check(numberOfNumbers == 5)
    }

    exercise("Check if an element exists or not") {
      val contains3: Boolean = numbers.contains(3)
      val contains6: Boolean = numbers.contains(6)

      check(contains3)
      check(!contains6)
    }

    exercise("Append (at the end of the list) the number 6") {
      val numbersThen6: List[Int] = numbers :+ 6

      check(numbersThen6 == List(1, 2, 3, 4, 5, 6))
    }

    exercise("Prepend (at the beginning of the list) the number 0") {
      val numbersAfter0: List[Int] = 0 +: numbers

      check(numbersAfter0 == List(0, 1, 2, 3, 4, 5))
    }

    /**
     * The map function is one of the most used one, it allows us to
     * easily update all elements of a list applying the same function
     * to all of them.
     */
    exercise("Transform all elements of a list using `map`") {
      val numbersTimes2: List[Int] = numbers.map(_ * 2)

      check(numbersTimes2 == List(2, 4, 6, 8, 10))
    }

    /**
     * Tips: You can use modulo to assert if a number is even or odd.
     */
    exercise("Filter all odd elements of a list using `filter`") {
      val evenNumbers: List[Int] = numbers.filter(_ % 2 == 0)

      check(evenNumbers == List(2, 4))
    }
  }

  /**
   * In Scala, you should not use the keyword `null` to specify than a
   * value can be undefined. Often, it leads to unexpected error in your
   * code because it is invisible and unpredictable. Tony Hoare, the
   * creator of null, call it the
   * [[https://hinchman-amanda.medium.com/null-pointer-references-the-billion-dollar-mistake-1e616534d485 one billion dollar mistake]].
   *
   * Instead, in Scala, you should use Option[A]. It is a data structure
   * that can be represented in two ways.
   *
   * It can be either:
   *   - None meaning that there is no value associated with the option.
   *   - Some(a) meaning that there is a value called `a` associated
   *     with the option.
   *
   * The main advantages compare to null is the compiler forcing you to
   * handle the case when the may not have a value.
   *
   * An Option[A] contains a value of type A. As an example, an
   * Option[Int] may contain an Int ot Nothing.
   *
   * INFO: Since Scala 3, it is safer to use null compared to Scala 2 or
   * Java since you need explicitly to declare that the value may be
   * null:
   *
   * {{{
   *   val intOrNull: Int | Null = null
   * }}}
   *
   * The "|" means that the value can be of type Int OR Null.
   */
  section("Practicing with Option[A]") {
    exercise("Create an option with no value") {
      val none: Option[Int] = None

      check(none.isEmpty)
    }

    exercise("Create an option with a value of 10") {
      val some: Option[Int] = Some(10)

      check(some.isDefined)
    }

    /** Try to apply what you learn with list here. */
    exercise("Modify the value inside the Option adding 10 to the current result") {
      val some: Option[Int]        = Some(10)
      val updatedSome: Option[Int] = some.map(_ + 10)

      check(updatedSome.contains(20))
    }

    /**
     * This one is trickier. However it is important to manipulate
     * options without having to handle the null value when it is not
     * necessary.
     */
    exercise("Chain two options") {
      val maybeUsername: Option[String] = Some("student@school.com")
      val maybePassword: Option[String] = Some("I luv scala 123")
      val maybeCredentials: Option[(String, String)] =
        maybeUsername.flatMap(username => maybePassword.map(password => (username, password)))

      check(maybeCredentials.contains(("student@school.com", "I luv scala 123")))
    }

    /**
     * At some point, you will certainly want to handle the none case.
     * You can for example give a default value if there is a none.
     */
    exercise("Recover from a none value using a default value of 10") {
      val none: Option[Int] = None
      val value: Int        = none.getOrElse(10)

      check(value == 10)
    }

    /**
     * You can also pattern match and handle the error differently thant
     * the value case.
     */
    exercise("Return the string 'There is no value' if it is none or 'There is a value' if it is some") {
      val none: Option[Int] = None
      val res: String =
        none match {
          case Some(_) => "There is a value"
          case None    => "There is no value"
        }

      check(res == "There is no value")
    }
  }

  /**
   * Options are good to describe a value that may not exists. However
   * it doesn't tell us why the value is absent. It is ok if the value
   * has only one reason to be absent since we can assume why we can't
   * get the value.
   *
   * However if the value is not available for several reasons we have
   * another data structure for it, the Either[E, A].
   *
   * Either[E, A] takes two type parameters instead of one:
   *   - A is the value if it is present
   *   - E is the error if the value is not present
   *
   * It can be either:
   *   - Left(e) meaning that there is an error `e` associated with the
   *     either.
   *   - Right(a) meaning that there is a value called `a` associated
   *     with the either.
   */
  section("Practicing with Either[A, E]") {
    exercise("Create an option with the string error 'Wrong code'") {
      val left: Either[String, Int] = Left("Wrong code")

      check(left.swap.contains("Wrong code"))
    }

    exercise("Create an option with a value of 200") {
      val right: Either[String, Int] = Right(200)

      check(right.contains(200))
    }

    /** Try to apply what you learn with list here. */
    exercise("Modify the value inside the Either adding 10 to the current result") {
      val either: Either[String, Int]        = Right(200)
      val updatedEither: Either[String, Int] = either.map(_ + 10)

      check(updatedEither.contains(210))
    }

    /**
     * It is similar to Option. If both either are errors, it will
     * return the first error (it is call the
     * [[https://en.wikipedia.org/wiki/Fail-fast fail fast principle]]).
     */
    exercise("Chain two either") {
      val maybeUsername: Either[String, String] = Right("student@school.com")
      val maybePassword: Either[String, String] = Left("The password should contain symbols.")
      val maybeCredentials: Either[String, (String, String)] =
        maybeUsername.flatMap(username => maybePassword.map(password => (username, password)))

      check(maybeCredentials == Left("The password should contain symbols."))
    }

    exercise("Recover from an error value using a default value of 10") {
      val either: Either[String, Int] = Left("The value is missing")
      val value: Int                  = either.getOrElse(10)

      check(value == 10)
    }
  }

  /**
   * You maybe noticed that the three data structures has many
   * similarities. The Scala library is built with this constraint in
   * mind.
   *
   * Therefore, in category theory, these data structures are all called
   * Monad. The word might be scary, it just design data structures that
   * contains:
   *   - the `map` function that allows to transform the content of the
   *     data structure
   *   - the `flatMap` function that allows to chain two data structures
   *   - the `pure` or `apply` function that allows to wrap a value into
   *     one of the data structure
   *
   * We already used these three functions, we will see them in details
   * here.
   */
  section("Similarities and monads") {
    exercise("Train to use the `pure` function wrapping 10 in List, Option and Either") {
      // Let's take a value of any type
      val value: Int = 10

      // We can wrap this value into the context of a monad

      // It give to the value a new capabilities:

      // For list, wrapping a value means that we can have several value of its type
      val listContainingTheValue: List[Int] = List(10)

      // For option, wrapping a value means that we may not have a value thus returning None
      val optionContainingTheValue: Option[Int] = Option(10)

      // For either, wrapping a value means that the value may be not available thus return an error of type E
      val eitherContainingTheValue: Either[Nothing, Int] = Right(10)

      check(listContainingTheValue.contains(10))
      check(optionContainingTheValue.contains(10))
      check(eitherContainingTheValue.contains(10))
    }

    /**
     * When a value is wrapped, we need a way to manipulate it. Indeed,
     * you can't do something like:
     * {{{
     *   val optionPlus2 = Some(2) + 4 // error
     * }}}
     *
     * You still can unwrap the value. For example, for an option,
     * giving a default value when the value is missing:
     * {{{
     *   val optionOr2: Int = None.getOrElse(2) // Note that this returns an Int, the content of the some OR 2
     * }}}
     *
     * However generally, it is cumbersome and you want to keep going in
     * the context of the monad.
     *
     * We use the function `map` to apply a function to the context.
     */
    exercise("Train to use the `map` applying the function f to the content of the Monad") {
      // Let's take a function to apply to our value
      def f(value: Int): Int = value + 10

      // Copy past the previous exercice answers
      val listContainingTheValue: List[Int]              = List(10)
      val optionContainingTheValue: Option[Int]          = Option(10)
      val eitherContainingTheValue: Either[Nothing, Int] = Right(10)

      // Applying the function f to a List of Int will apply this function to every element of the List.
      val listContainingTheValueApplyingF: List[Int] = listContainingTheValue.map(f)
      check(listContainingTheValueApplyingF.contains(20))

      // Applying the function f to an Option will apply the function to the value if it exists or will be ignored if none.
      val optionContainingTheValueApplyingF: Option[Int] = optionContainingTheValue.map(f)
      check(optionContainingTheValueApplyingF.contains(20))

      // Applying the function f to an Either will apply the function to the value if it exists or will be ignored if it is an error.
      val eitherContainingTheValueApplyingF: Either[Nothing, Int] = eitherContainingTheValue.map(f)
      check(eitherContainingTheValueApplyingF.contains(20))
    }

    exercise("Train to use the `flatMap` summing the content of two monad") {
      // Copy past the previous exercice answers
      val listContainingTheValue: List[Int]              = List(10)
      val optionContainingTheValue: Option[Int]          = Option(10)
      val eitherContainingTheValue: Either[Nothing, Int] = Right(10)

      // In our program, we will have multiple times an optional value, a list of value or a value that may produce an error
      val listContainingAnotherValue: List[Int]              = List(20)
      val optionContainingAnotherValue: Option[Int]          = Option(20)
      val eitherContainingAnotherValue: Either[Nothing, Int] = Right(20)

      // Sum the content of the Lists
      val listSum: List[Int] =
        listContainingTheValue.flatMap(theValue =>
          listContainingAnotherValue.map(anotherValue => theValue + anotherValue)
        )
      check(listSum == List(30))

      // Sum the content of the Options
      val optionSum: Option[Int] =
        optionContainingTheValue.flatMap(theValue =>
          optionContainingAnotherValue.map(anotherValue => theValue + anotherValue)
        )
      check(optionSum == Option(30))

      // Sum the content of the Eithers
      val eitherSum: Either[Nothing, Int] =
        eitherContainingTheValue.flatMap(theValue =>
          eitherContainingAnotherValue.map(anotherValue => theValue + anotherValue)
        )
      check(eitherSum == Right(30))
    }

    /**
     * FlatMap a list maybe a bit hard to understand, a good use case
     * for it is the following:
     *   - You have a list of words and you need to retrieve a list of
     *     characters in uppercase.
     */
    exercise("Common use case to flatMap a list") {
      val words   = List("scala", "is", "great")
      val letters = words.flatMap(_.toUpperCase)

      check(letters == List('S', 'C', 'A', 'L', 'A', 'I', 'S', 'G', 'R', 'E', 'A', 'T'))
    }

    /**
     * FlatMap can be a bit cumbersome to write and to understand, it
     * exists another way to write this kind of computation called `for
     * comprehension`.
     *
     * The `for comprehension` is just what we call
     * [[https://en.wikipedia.org/wiki/Syntactic_sugar syntactic sugar]],
     * the compiler will use `flatMap` and `map` under the hood. It
     * exists only for developer to have a more readable and writable
     * code.
     *
     * Here is an equivalent for list:
     * {{{
     *   val listContainingTheValue: List[Int]     = List(10)
     *   val listContainingAnotherValue: List[Int] = List(20)
     *
     *   val listSum: List[Int] =
     *      listContainingTheValue.flatMap(
     *          theValue => listContainingAnotherValue.map(anotherValue => theValue + anotherValue)
     *      )
     *
     *   val listSum2: List[Int] = for {
     *      theValue <- listContainingTheValue
     *      anotherValue <- listContainingAnotherValue
     *   } yield theValue + anotherValue
     * }}}
     */
    exercise("For comprehension syntactic sugar") {
      // Do the same for options

      val optionContainingTheValue: Option[Int]     = Option(10)
      val optionContainingAnotherValue: Option[Int] = Option(20)

      val optionSum: Option[Int] =
        optionContainingTheValue.flatMap(theValue =>
          optionContainingAnotherValue.map(anotherValue => theValue + anotherValue)
        )

      val optionSum2: Option[Int] =
        for {
          theValue     <- optionContainingTheValue
          anotherValue <- optionContainingAnotherValue
        } yield theValue + anotherValue

      check(optionSum == optionSum2)
    }
  }
