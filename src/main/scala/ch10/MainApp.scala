package ch10

/**
  * Created by chengyongqiao on 29/07/2017.
  */
object MainApp extends App {

    util.PrintUtil.printChapterTitle(10, "Trait")
    println()
    util.PrintUtil.printKeyPoints("A class can implement any numbers of traits." ,
        "Trait can require implementing classed to have certain fields, methods, " +
        "or superclass" ,
        "Unlike Java interfaces, a Scala trait can provide implementations of " +
        "methods and fields." ,
        "When you layer multiple traits, the order matters-the trait whose methods "+
        "execute first goes to the back.")



    trait Logger {
        def log(msg: String)
    }

    trait ConsoleLogger extends Logger {
        def log(msg: String): Unit = println(msg)
    }

    abstract class Account {
        def withdraw(amount: Double)
    }

    class SavingsAccount extends Account with ConsoleLogger {
        def withdraw(amount: Double): Unit = {
            log(s"withdraw $amount")
        }
    }

    var account: Account = null
    account = new SavingsAccount
    account.withdraw(100.0)

    //leave the log abstract
    abstract class AnotherAccount extends Account with Logger {
        def withdraw(amount: Double): Unit = {
            log(s"withdraw $amount")
        }
    }
    val account2 = new AnotherAccount with ConsoleLogger //mix a concrete logger
    account2.withdraw(100)

    trait ShortConsoleLogger extends ConsoleLogger {
        val maxLength: Int //abstract
        override def log(msg: String): Unit = {
            super.log( if (msg.length < maxLength) msg else msg.substring(0, (maxLength-3)) + "...")
        }
    }


    trait TimestampLogger extends ConsoleLogger {
        override def log(msg: String): Unit = {
            val now = java.time.Instant.now()
            super.log(s"$now $msg")
        }
    }


    val account3 = new AnotherAccount with TimestampLogger
    account3.withdraw(1000)

    val account4 = new AnotherAccount with ConsoleLogger with TimestampLogger with ShortConsoleLogger {
        override val maxLength: Int = 50
    }
    account4.withdraw(1)


    trait LoggedException extends Exception with ConsoleLogger {
        def log(): Unit = super.log(getMessage)
    }

    class UnhappyException extends LoggedException {
        override def getMessage: String = "angry"
    }

    val ue = new UnhappyException
    ue.log()

    trait AnotherLoggedException extends ConsoleLogger {
        this: Exception =>
        def log(): Unit = log(getMessage)
    }

    class AnotherUnhappyException extends Exception {
        override def getMessage: String = "angry"
    }

    val aue = new AnotherUnhappyException with AnotherLoggedException
    aue.log()
}
