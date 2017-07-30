package ch11

/**
  * Created by chengyongqiao on 30/07/2017.
  */
object MainApp extends App {

    util.PrintUtil.printChapterTitle(11, "Operators")
    println()
    util.PrintUtil.printKeyPoints("Identifiers contain either alphanumeric or operator characters.",
        "Unary and binary operators are method calls.",
        "Operator precedence depends on the first character, associativity on the last. ",
        "The apply and update methods are called when evaluating expr(args).",
        "Extractors extract tuples or sequences of values from an input.",
        "Types extending the Dynamic trait can inspect the names of methods and arguments at runtime.")


    //class Fraction(val num: Int, val den: Int) {

        //private var n: Int = num
        //private var d: Int = den
    class Fraction(val n: Int, val d: Int) {

        override def toString: String = s"$n/$d"

        def *(other: Fraction) = new Fraction(n * other.n, d * other.d)

        def +(other: Fraction) = new Fraction(n * other.d + other.n * d, d * other.d)

        def /(other: Fraction) = *(new Fraction(other.d, other.n))

        def -(other: Fraction) = new Fraction(n * other.d - other.n * d, d * other.d)

        def unary_- = new Fraction(n, -d)

        def +=(other: Fraction): Fraction = {
            val newn = n * other.d + other.n * d
            val newd = d * other.d
            new Fraction(newn, newd)
        }
    }

    object Fraction {
        def apply(n: Int, d: Int): Fraction = new Fraction(n, d)

        def unapply(arg: Fraction): Option[(Int, Int)] = {
            if (arg.d == 0) None else Some(arg.n, arg.d)
        }
    }

    val f1 = new Fraction(1, 3)
    val f2 = new Fraction(1, 4)
    val f3 = f1 * f2
    val f4 = f1 + f2
    val f5 = f1 / f2
    val f6 = f1 - f2
    println(f1, f2, f3, f4, f5, f6)
    println(-f1, -f2, -f1 * f2, -f1 * -f2)
    val f7 = f1
    val f8 = f7 += f2
    println(f7, f8)

    import scala.language.postfixOps
    println(43 toString)
    println(43 + 42 toString)

    val Fraction(n, d) = f8
    println(n, d)

    val f9 = Fraction(1, 0)
    /*val Fraction(n1, d1) = f9
    println(n1, d1)*/
    val tupleOption = Fraction.unapply(f9)
    if (tupleOption.isEmpty) println("empty") else println(tupleOption.get._1, tupleOption.get._2)

    case class Name(val firstName: String, val lastName: String)
    val name = Name("sion", "cheng")
    val Name(first, last) = name
    println(first, last)

    object IsCompound {
        def unapply(arg: String): Boolean = arg.contains(" ")
    }

    val author1 = Name("sion", "cheng")
    val author2 = Name("sion", "van der Linden")
    def isLastNameCompound(name: Name): Boolean = {
        name match {
            case Name(_, IsCompound()) =>
                true
            case Name(_, _) =>
                false
        }

    }
    println(isLastNameCompound(author1), isLastNameCompound(author2))

    class Number (val n: String) {
        private var number: String = ""
        if ("[0-9]+".r.pattern.matcher(n).find()) {
            number = n
        } else {
            throw new IllegalArgumentException("format wrong")
        }
    }

    object Number {
        def apply(s: String) = new Number(s)
        def unapply(arg: Number): Option[String] = Some(arg.number)
    }

    object NumberExtractor {
        def unapplySeq(arg: String): Option[Seq[String]] = {
            if (arg == "") None else Some(arg.toCharArray.map(_.toString))
        }
    }

    val num  = new Number("12345")
    num match {
        case Number(one) =>
            println(one)
        case _ =>
            println("what?")
    }
    "12345" match {
        case NumberExtractor(n1, n2, n3, n4, n5) =>
            println(n1, n2, n3, n4, n5)
        case _ =>
            println("what?")

    }

    import scala.language.dynamics
    class Person extends Dynamic {
        def selectDynamic(select: String) = println(s"select dynamic $select")
        def applyDynamic(name: String)(arg1: Int*): Unit = {
            println(s"apply dynamic $name ${arg1.mkString(",")}")
        }
        def updateDynamic(name: String)(v: String): Unit = {
            println(s"update dynamic $name $v")
        }
        def applyDynamicNamed(name: String)(arg: (String, Int) *): Unit = {
            println(s"apply dynamic named $name ${arg.mkString(",")}")
        }
    }
    val p = new Person
    p.hello
    p.hi(1,2,3)
    p.ha = "1"
    p.ho(one = 1, two = 2, three = 3, 4, 5)
}
