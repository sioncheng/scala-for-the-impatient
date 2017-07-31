package ch12


object MainApp extends App {

    util.PrintUtil.printChapterTitle(12, "high order functions")
    println()
    util.PrintUtil.printKeyPoints("Functions are first class citizens in " +
        "scala, just like numbers." ,
        "You can create anonymous functions, usually to give them to other functions.",
        "A function argument specifies behavior that should be executed later." ,
        "Many collection methods take function parameters, applying a function " +
        "to the values of the collection." ,
        "There are syntax shortcuts that allow you to express function parameters " +
        "in a way that is short and easy to read." ,
        "You can create functions that operate on blocks of code and look much " +
        "like the build-in control statements.")

    import scala.math._
    val num = Pi
    val fun = ceil _
    println(fun(num))

    val list = List(1.1, 2.2, 3.3, 4.4)
    println(list.map(fun))
    println(list.map(x => x * 2))
    val triple = (x: Double) => 3 * x
    println(list.map(triple))

    def valueAtOneQuarterOf(f : (Double) => Double) = f(0.25)
    println(valueAtOneQuarterOf(fun))
    println(valueAtOneQuarterOf(triple))

    def mulBy(factor: Double) = (x: Double) => factor * x
    println(mulBy(1)(2))
    println(list.map(mulBy(2)))

    println(valueAtOneQuarterOf(_ * 4))

    val ito9 = (1 to 9)
    println(ito9.map(0.1 * _))
    ito9.map("*" * _).foreach(println _)
    println(ito9.filter(_ % 2 == 0))
    println(ito9.reduceLeft(_ * _))
    println(ito9.reduceLeft(_ / _))
    println("Scala is awesome".split(" ").sortWith(_.length < _.length))

    val mul = (x: Int, y: Int) => x * y
    val mulOne = (x: Int) => ((y: Int) => x *y)
    println(mul(3,4))
    println(mulOne(3)(4))
    def mulOneAnother(x: Int)(y: Int) = x * y
    println(mulOneAnother(4)(3))


    def ha(a: String)(b:String)(f: (String, String) => Boolean) = f(a,b)
    println(ha("a")("b")(_.equalsIgnoreCase(_)))
    println(ha("b")("b")(_.equalsIgnoreCase(_)))

    def runInThread(block: => Unit): Unit = {
        new Thread{
            override def run(): Unit = { block }
        }.start()
    }

    runInThread{println("1")}

    def runInThreadAnother(block: ()=> Unit): Unit = {
        new Thread{
            override def run(): Unit = block
        }.start()
    }

    runInThreadAnother(() => println("1"))
}
