package ch21

object MainApp extends App {

    util.PrintUtil.printChapterTitle(21, "implicits")
    println()
    util.PrintUtil.printKeyPoints("Implicit conversions are used to convert between types.",
    "You must import implicit conversions so that they are in scope.",
    "An implicit parameter list requests objects of a given type. The can be obtained " +
    "from implicit objects that are in scope, or from the companion object of the desired " +
    "type.",
    "If an implicit parameter is a single-argument function, it is also used as an implicit " +
    "conversion.",
    "A context bound of a type parameter requires the existence of an implicit object of " +
    "the given type.",
    "If it is possible to locate an implicit object, this can serve as evidence that a " +
    "type conversion is valid.")
    println()

    import ch11.MainApp.Fraction

    implicit def int2Fraction(n: Int) = Fraction(n, 1)

    val result = 3 * ch11.MainApp.Fraction(4, 1)

    println(result)

    trait BetterFraction {
        def betterF(): Unit
    }

    def needBetterFraction(f : BetterFraction): Unit = {
        f.betterF()
    }

    implicit class RichFraction(val from: Fraction) extends BetterFraction {
        override def betterF(): Unit = {
            println(from.toString)
        }
    }

    needBetterFraction(ch11.MainApp.Fraction(5,1))

    implicit def fractionToDouble(f: Fraction) = f.n * 1.0 / f.d

    val result2 = 4 * Fraction(4, 1)
    println(result2)

    println(5.d, 5.n)

    println(Fraction(4,1) * 4)

    def mul(a: Int)(implicit b: Int) = a * b
    println(mul(2)(3))
    implicit val b = 100
    println(mul(2))

    case class Delimiters(left: String, right: String)
    object Delimiters {
        implicit val default = Delimiters("[[", "]]")
    }

    def quote(q: String)(implicit delimiters: Delimiters): String = {
        s"${delimiters.left} $q ${delimiters.right}"
    }

    println(quote("aha"))
    println(quote("aha")(Delimiters("<", ">")))

    def consumeFraction(a: Int, b: Int)(implicit f: (Int, Int) => Fraction): Unit = {
        println(f(a,b))
    }

    implicit def intPairToFraction(x: Int, y: Int): Fraction = new Fraction(x, y)

    consumeFraction(1,2)

    trait Smaller[T] {
        def isSmaller(a: T, b: T): Boolean
    }

    class Pair[T: Smaller] (val first: T, val second: T) {
        def smaller(implicit ord: Smaller[T]) = if (ord.isSmaller(first, second)) first else second
    }

    implicit object IntSmaller extends Smaller[Int] {
        override def isSmaller(a: Int, b: Int): Boolean = a < b
    }

    implicit object FractionSmaller extends Smaller[Fraction] {
        override def isSmaller(a: Fraction, b: Fraction): Boolean = a - b <= 0
    }

    val p = new Pair(4, 3)
    println(p.smaller)

    val p2 = new Pair[Fraction](new Fraction(2,1), new Fraction(4,5))
    println(p2.smaller)

    def firstLast[A, L](it: L)(implicit evidence: L <:< Iterable[A]) = (it.head, it.last)

    println(firstLast(List(1,2,3)))
}
