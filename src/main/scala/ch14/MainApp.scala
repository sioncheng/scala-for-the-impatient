package ch14

import ch6.MainApp.Account

object MainApp extends App {

    util.PrintUtil.printChapterTitle(14, "pattern matching and case class")
    println()
    util.PrintUtil.printKeyPoints("The match expression is a better switch , " +
        "without fall-through." ,
        "If no pattern matches, a MatchError is thrown. Use the case _ " +
        "pattern to avoid that.",
        "A pattern can include an arbitrary condition, called a guard.",
        "You can match on the type of an expression; prefer this over " +
        "instanceOf/asInstanceOf.",
        "You can match patterns of arrays, tuples, and classes, and bind " +
        "parts of the pattern to variables.",
        "In a for expression, nonmatches are are silently skpped." ,
        "A case class is a class for which the compiler automatically " +
        "produces the methods that are needed for pattern matching.",
        "The common superclass in a case class hierarchy should be sealed.",
        "Use the Option type for values that may or may not be present.")

    var sign: Int = -2
    val ch: Char = '+'

    ch match {
        case '+' =>
            sign = 1
        case '-' =>
            sign = -1
        case _ =>
            sign = 0
    }

    def checkSign(char: Char) = char match {
        case '+' => 1
        case '-' => -1
        case _ if Character.isDigit(char) => Character.digit(char, 10)
        case _ => 0
    }

    val sign2 = checkSign(ch)

    println(sign, sign2, checkSign('9'))

    def checkType(o: Any):String = o match {
        case _: Int => "Int"
        case _: String => "String"
        case _: Char => "Char"
        case _: Double => "Double"
        case _ => "what?"
    }
    println(checkType(1), checkType('1'), checkType("1"), checkType(1.0))

    def checkArray(a: Array[Int]): String = a match {
        case Array(0) => "0"
        case Array(x, y) => s"$x $y"
        case Array(0, _*) => "0 ..."
        case _ => "what?"
    }

    println(checkArray(Array(0)), checkArray(Array(1,2)), checkArray(Array(0,45)))

    val Array(first, second, rest @ _*) = Array(1,2,3,4,5,6)
    println(first, second, rest.max)

    sealed abstract class Amount
    case class Dollar(value: Double) extends Amount
    case class Currency(value: Double, unit: String) extends Amount
    case object Nothing extends Amount

    def checkAmount(a: Amount) : String = a match {
        case Dollar(v) => s"$$$v"
        case Currency(v, u) => s"$v $u"
        case Nothing => ""
    }

    println(checkAmount(Dollar(1)), checkAmount(Currency(1, "Yuan")), checkAmount(Nothing))

    val a1 = Currency(1, "Yuan")
    val a2 = a1.copy(unit = "$")
    println(a2.toString)

    sealed abstract class TrafficLightColor
    case object Red extends TrafficLightColor
    case object Yellow extends TrafficLightColor
    case object Green extends TrafficLightColor

    def checkColor(c: TrafficLightColor) = c match {
        case Red => "red"
        case Yellow => "yellow"
        case Green => "green"
    }

    println(checkColor(Red))

    val f: PartialFunction[Char, Int] = {
        case '+' => 1
        case '-' => -1
    }

    println(f.isDefinedAt('+'), f.isDefinedAt('-'), f.isDefinedAt('f'))

    val names = Array("A", "B", "C") //PartialFunction[Int, String]
    val scores = Map("A"->10, "B"->8, "C"->9) //PartialFunction[String, Int]
    names.collect(scores).foreach(print)
    println()

}
