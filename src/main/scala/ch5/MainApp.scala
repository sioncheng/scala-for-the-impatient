package ch5

import scala.beans.BeanProperty

object MainApp extends App {

    util.PrintUtil.printChapterTitle(5, "Classes")
    println()
    util.PrintUtil.printKeyPoints("Fields in classed automatically come with " +
        "getters and setters.",
        "You can replace a filed with a custom getter/setter without " +
        "changing the client of class-that is the \"uniform access principle\".",
        "Use the @BeanProperty annotation to generate the JavaBeans getXxx/" +
        "setXxx methods.",
        "Every class has a primary constructor that is \"interwoven\" with " +
        "the class definition. Its parameters turn into the fields of class. " +
        "The primary constructor executes all statements in the body of class.",
        "Auxiliary constructors are optional. The are called this.")
    println()

    class Counter {
        private[this] var value = 0 //object private
        def increment() {value += 1}
        def current() = value
        def currentVal = value

        //def isLess(other: Counter) = value < other.value
        // not able to access other.value
        def isLess(other: Counter) = value < other.currentVal
    }

    val myCounter = new Counter
    myCounter.increment()
    println(myCounter.current())
    println(myCounter.currentVal)

    val myCounter2 = new Counter
    myCounter2.increment()
    myCounter2.increment()
    println(myCounter2.isLess(myCounter))


    class Person {
        private var privateAge = 0

        def age = privateAge
        def age_=(newValue: Int): Unit = { //this syntax is ugly
            privateAge = newValue
        }

        @BeanProperty var name: String = _ //syntax is ugly too
    }

    val fred = new Person
    fred.age = 30
    println(fred.age)
    fred.setName("fred")
    println(fred.getName)

    class Employee {
        private var _name: String = _
        private var _age: Int = _

        def this(name: String) {
            this() //must
            this._name = name
            this._age = 10
        }

        def this(name: String, age: Int) {
            this(name)
            this._age = age
        }

        def name = _name
        def age = _age
        def name(name: String): Unit = {
            _name = name
        }
        def age(age: Int): Unit = {
            _age = age
        }
    }

    val bill = new Employee("bill", 20)
    println(bill.name)
    bill.age(30)
    println(bill.age)

    class Point(val x: Int, val y: Int) {
        def description = s"($x, $y)"
    }
    val p = new Point(2,4)
    println(p.x, p.y)
    println(p.description)

    class FloatPoint(var x: Float, var y: Float) {}
    val fp = new FloatPoint(4.0f, 5.0f)
    println(fp.x, fp.y)
    fp.x = 5.0f
    fp.y = 5.0f
    println(fp.x, fp.y)
}
