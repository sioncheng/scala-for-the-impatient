package ch1

/**
  * Created by cyq on 25/07/2017.
  */
object MainApp extends App {

    println(1.to(10)) //Range 1 to 10
    println("hello".intersect("world")) // lo

    val ten2twenty = 10 to 20
    println(ten2twenty)

    import scala.math._
    println(sqrt(10))
    println(min(3, 10))

    println(BigInt("12342424"))
    println("hello"(1))
    println(BigDecimal.apply("10.0"))

    val what = "what are you doing"
    println(what.count(x => x.compareTo('d') > 0))
    println(what.map(x => x.toUpper))
    println(what.containsSlice('a'.to('d')))
    println(what.containsSlice('w'.to('w')))
    println(what.head)
    println(what.last)
}
