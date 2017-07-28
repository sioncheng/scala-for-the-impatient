package ch8

object MainApp extends App {

    util.PrintUtil.printChapterTitle(8, "inheritance")
    println()


    class Person(val name: String, val age: Int) {
        override def toString: String = s"${getClass.getName}[name=$name]"

        final override def equals(obj: scala.Any): Boolean = {
            obj match {
                case that: Person => that.name.equals(name) && that.age.equals(age)
                case _ => false
            }
        }
    }

    class Employee(name: String, age: Int, val salary: Double) extends Person(name, age) {

    }

    val p = new Person("jerry", 30)
    val p2 = new Person("jerry", 30)
    val p3 = new Person("tom", 30)
    println(p.toString)
    println(p2.equals(p))
    println(p3.equals(p))
    if (p.isInstanceOf[Person]) println("p is instance of person")
    if (p.getClass == classOf[Person]) println("p is instance of person")

    p match {
        case _: Person => println("p is instance of person")
        case _ => println("p is something other")
    }

    class MyTime private (val time: Int) extends AnyVal {
        def minutes = time % 60
        def hours = time / 60

        override def toString: String = s"$hours: $minutes"
    }

    object MyTime {
        def apply(time: Int):MyTime = new MyTime(time)
    }

    val mt = MyTime(100)
    println(mt.toString)
}
