package ch13

import scala.collection.{BitSet, Iterator, SortedSet, mutable}

object MainApp extends App {

    util.PrintUtil.printChapterTitle(13, "Collection")
    println()
    util.PrintUtil.printKeyPoints("All collections extend the Iterable trait.",
        "The three major categories of collections are sequences, sets and maps.",
        "Scala has mutable and immutable versions of most collections.",
        "A Scala list is either empty, or it has a head and a tail which is again a list.",
        "Sets are unordered collections.",
        "Use a LinkedHashSet to retain the insertion order or a SortedSet " +
        "to iterate in sorted order.",
        "+ adds an element to an unordered collection; +: and :+ prepend or " +
        "append to a sequence; ++ concatenates two collections; - and -- " +
        "remove elements.",
        "The Iterable and Seq traits have dozens of useful methods for common " +
        "operations. Check them out before writing tedious loops.",
        "Mapping, folding, and zipping are useful techniques for applying " +
        "a function or operation to the elements of a collection."
    )

    def printIterableColl[A](iter: Iterator[A]) : Unit = {

        while(iter.hasNext) {
            print(iter.next())
        }
        println()
    }

    val coll = 1 to 10
    val iter = coll.iterator
    printIterableColl(iter)

    val iterable2 = Iterable(1,2,3,4,5,6,7,8,9)
    printIterableColl(iterable2.iterator)

    printIterableColl(SortedSet(1,2,3).iterator)

    val numbers = 1 to 5
    val numbers2 = numbers :+ 6
    printIterableColl(numbers2.iterator)

    val digits = List(4, 2)
    println(digits.head)
    println(digits.tail)
    println(9 :: digits)
    println(digits :+ 0)
    println(9 :: 4 :: 2 :: Nil)

    def sum(list: List[Int]): Int = list match {
        case Nil =>
            0
        case h :: t => h + sum(t)
    }

    println(sum(List(1,2,3)))
    println(List(1,2,3).sum)
    println(Set(2,0,1) + 1)

    Set(1,2,3,4,5).foreach(print _)
    println()
    mutable.LinkedHashSet(1,2,3,4,5).foreach(print _)
    println()
    SortedSet(1,2,5,4,3).foreach(print _)
    println()
    BitSet(1,1,0,1,1).foreach(print _)
    println()

    List("One", "two", "THREE").map(_.toUpperCase).foreach(print _)
    println()

    "-3+4".collect{
        case '+' => 1;
        case '-' => -1;
        case _ => 0
    }.foreach(print)
    println()

    (1 to 10).groupBy(_+10.toString).foreach(x => print(x._1, x._2))
    println()
    (1 to 10).groupBy(_ % 2 == 0).foreach(x => print(x._1, x._2))
    println()

    println(List(1,7,2,9).reduceLeft(_ - _)) // 1 - 7 - 2 - 9
    println(List(1,7,2,9).reduceRight(_ - _)) // 1 - (7 - (2 - 9))
    println((0 /: List(1,7,2,9))(_ - _))
    //println((0 :\ List(1,7,2,9))(_ - _))
    println((List(1,7,2,9) :\ 0)(_ - _))

    println(List(1,2).zip(List("One", "Two")))
    List(1,2).zip(List(2,4)).map(x => x._2 * x._1).foreach(print)
    println()
    List(1,2,3).zip(List(2,4)).map(x => x._2 * x._1).foreach(print)
    println()
    List(1,2,4).zip(List(2,4)).foreach(print)
    println()
    "Scala".zipWithIndex.foreach(print)
    println()

    val bi = (1 to 10).iterator.buffered
    while(bi.hasNext && bi.head % 2 == 0) {
        print(bi.next())
    }
    println()

    def numbersFrom(n: Int): Stream[Int] = n #:: numbersFrom(n + 1)
    //int start from n
    val n10 = numbersFrom(10)
    println(n10)
    println(n10.tail.tail)
    println(n10.take(5).force)

    val thousand = (1 to 1000).view
    println(thousand)
    println(thousand.take(10).mkString(","))

    println((1 to 1000000).par.sum)
}
