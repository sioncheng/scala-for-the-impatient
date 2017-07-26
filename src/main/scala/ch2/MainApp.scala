package ch2

/**
  * Created by cyq on 26/07/2017.
  */
object MainApp extends App {
    println("chapter 2")
    println()
    println("[] An if expression has value")
    println("[] A block has a value, the value of its last expression.")
    println("[] The scala for loop is like an enhanced java for loop")
    println("[] Semicolons are (mostly) optional")
    println("[] The void type is Unit")
    println("[] Avoid using return in a function")
    println("[] Beware of missing = in a function definition")
    println("[] Exceptions work just like in java or c++, but you use a pattern matching syntax for catch")
    println("[] Scala has no checked exceptions")
    println()

    //an if expression has value
    val x = 100
    val s = if (x > 0) 1 else -1
    println(s)
    val s2 = if (x < 1000) "less than 1000" else 0
    println(s2)
    val s3 = if (x > 0) 1 else if (x == 0) 0 else -1
    println(s3)

    val s4 = 1 + 2 + 3 +
        4 + 5 + 6
    println(s4)

    val s5 = { val dx = 100; println(dx); dx + 1000}
    println(s5)

    val s6 = {var xx = 100; println(xx); xx += 1000}
    println(s6)

    var y = 0
    val s7 = y = 100
    println(y)
    println(s7)

    var name = "Scala"
    var age = 10
    printf("Hello, %s! You are %d years old. %n", name, age)

    /*
    import scala.io._
    name = StdIn.readLine("Your name: ")
    print("Your age: ")
    age = StdIn.readInt()
    print(f"Hello, $name! Next year, you will be ${age+1} years old. %n")
    */

    var n = 10
    var m = 1
    while (n > 0) {
        m = m * n
        n -= 1
    }
    println(m)

    for (i <- 1 to 10) {
        println(i * i)
    }

    import scala.util.control.Breaks._
    breakable(
        for (i <- 1 to 10) {
            println(i * i * i)
            println("break")
            break()
        }
    )

    //this type of loop is called a for comprehension
    //the generated collection is compatible with the first generator
    for (i <- 1 to 3; j <- 1 to 3) print(f"$i * $j = ${i * j} %n")

    val cl = for (c <- "Hello"; i <-1 to 2) yield (c+i).toChar
    println(cl)

    val cl1 = for (c <- List("H","e", "l","l", "o"); i <- 1 to 2) yield (c+i)
    println(cl1)

    val cl2 = for {
        i <- 1 to 2
        c <- "Hello"
    } yield (c+i).toChar
    println(cl2)

    def abs(x: Double) = if (x >= 0) x else -x
    println(abs(100))
    println(abs(0))
    println(abs(-100))

    def fac(n: Int) = {
        var r = 1
        for (i <- 1 to n) r = r * i
        r
    }
    println(fac(10))

    def fac2(n: Int): Int = if (n <= 0) 1 else n * fac2(n-1)
    println(fac2(10))

    def decorate(str: String, left: String = "[", right: String = "]") =
        left + str + right
    println(decorate("hello"))
    println(decorate("hello", "{", "}"))
    println(decorate(left = "<<", right = ">>", str = "hello"))

    def sum(args: Int*) = {
        var result = 0
        for (arg <- args) result += arg
        result
    }
    println(sum(1,2,3,4,5))
    println(sum(1 to 5: _*))

}


