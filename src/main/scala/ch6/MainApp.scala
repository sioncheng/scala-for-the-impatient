package ch6

object MainApp extends App {

    util.PrintUtil.printChapterTitle(6, "Objects")
    println()
    util.PrintUtil.printKeyPoints("Use objects for singletons and " +
        "utility methods." ,
        "A class can have a companion object with the same name." ,
        "Objects can extends classes or traits." ,
        "The apply method of an object is usually used for constructing " +
        "new instances of the companion class." ,
        "To avoid the main method, use and object that extends the App trait.",
        "You can implement enumerations by extending the Enumeration object.")

    object Accounts {
        private var lastNumber = 0

        def newUniqueNumber(): Int = {
            lastNumber += 1;
            lastNumber;
        }
    }

    println(Accounts.newUniqueNumber())
    println(Accounts.newUniqueNumber())

    class Account(val id: Int, initBalance: Double) {
        private var balance = initBalance
        def deposit(amount: Double): Unit = {
            balance += amount
        }
        def withdraw(amount: Double): Unit = {
            balance -= amount
        }
        def currentBalance = balance
    }

    object Account {
        private var lastNumber = 0

        def newUniqueNumber(): Int = {
            lastNumber += 1;
            lastNumber;
        }

        def apply( initBalance: Double): Account =
            new Account(newUniqueNumber(), initBalance)
    }

    abstract class UndoableAction(val description: String) {
        def undo(): Unit
        def redo(): Unit
    }

    class DoNothingAction extends UndoableAction("Do nothing") {
        override def redo(): Unit = {}
        override def undo(): Unit = {}
    }

    val donothing = new DoNothingAction
    donothing.undo()

    val a = Account(1000)
    println(a.currentBalance)

    object TrafficLightColor extends Enumeration {
        val Red, Yellow, Green = Value
        val Blue = Value(100, "Blue")
    }

    println(TrafficLightColor.Blue,
        TrafficLightColor.Blue.id,
        TrafficLightColor.Blue.toString)
    println(TrafficLightColor.Red,
        TrafficLightColor.Red.id,
        TrafficLightColor.Red.toString)
    println(TrafficLightColor.Red > TrafficLightColor.Yellow)

}
