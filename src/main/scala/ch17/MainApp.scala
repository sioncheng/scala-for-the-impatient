package ch17

import java.util.concurrent.Executors


object MainApp extends App {

    util.PrintUtil.printChapterTitle(17, "futures")
    println()
    util.PrintUtil.printKeyPoints("A block of code wrapped in a Future {...} " +
        "executes concurrently.",
        "A future succeeds with a result or fails with an exception.",
        "You can wait for a future to complete, but you don't usually want to.",
        "You can use callbacks to get notified when a future completes, but " +
        "that gets tedious when chaining callbacks.",
        "Use methods such as map/flatMap , or the equivalent for expressions, "+
        "to compose futures.",
        "A promise has a future whose value can be set(once), which gives " +
        "added flexibility for implementing tasks that produce results." ,
        "Pick an execution context that is suitable for the concurrent " +
        "workload of your computation")

    import java.time._
    import scala.concurrent._
    import ExecutionContext.Implicits.global

    Future {
        Thread.sleep(1000)
        println(s"this is the future at ${LocalTime.now()}")
    }

    println(s"this is the present at ${LocalDate.now()}")


    Future {
        for (i <- 1 to 5) {
            print(s"A ${i} ")
            Thread.sleep(100)
        }
        println()
    }

    Future {
        for (i <- 1 to 5) {
            print(s"B ${i} ")
            Thread.sleep(100)
        }
        println()
    }

    val f = Future {
        Thread.sleep(1000)
        42
    }
    import scala.concurrent.duration._
    val result = Await.result(f, 2 seconds)
    println(result)
    //println(f.value)

    val f2 = Future {
        Thread.sleep(2000)
        43
    }
    Thread.sleep(1000)
    println(f2.value)
    Thread.sleep(2000)
    println(f2.value)

    import scala.util._
    Future {
        Thread.sleep(1000)
        44
    }.onComplete(u => {
        u match {
            case Success(v) =>
                println(v)
            case Failure(ex) =>
                println(ex)
        }
    })

    f.onComplete {
        case Success(v) =>
            f2.onComplete {
                case Success(v2) =>
                    println(v *v2)
                case Failure(ex) =>
                    println(ex)
            }
        case Failure(ex) =>
            println(ex)
    }

    f.map(v1 => f2.map(v2 =>  println(v1 + v2) ))

    f.flatMap(n1 => f2.map(n2 => n1 + n2)).onComplete(
        _ match {
            case Success(x) => println(x)
            case Failure(ex) => println(ex)
        }
    )

    (for (n1 <- f; n2 <- f2) yield n1 + n2).onComplete {
        case Success(x) => println(x)
        case Failure(ex) => println(ex)
    }

    (for (n1 <- f; n2 <- f2) yield n1 + n2).foreach(println)

    Future {
        Thread.sleep(100)
        throw new Exception("hahaha")
    } recover {
        case e: Exception =>println(e.getMessage, Thread.currentThread().getName)
        case _ => println("what?")
    }

    val p1 = Promise[Int]()

    def tellMe(p: Promise[Int]): Unit = {
        Future {
            //work hard to get data
            Thread.sleep(200)
            val n1 = 100
            p.success(n1)
            println("thread id", Thread.currentThread().getName)
        }
    }

    tellMe(p1)

    Thread.sleep(200)
    p1.future.onComplete {
        case Success(x) => println(x)
        case Failure(ex) => println(ex)
    }

    val pool = Executors.newCachedThreadPool()
    implicit val ec = ExecutionContext.fromExecutor(pool)
    Future {
        println("thread id", Thread.currentThread().getName)
    }

    scala.io.StdIn.readLine()

    pool.shutdown()
}
