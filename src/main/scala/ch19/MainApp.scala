package ch19

import ch19.MainApp.{Container, Listener}

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, Buffer, ListBuffer}
import scala.reflect.ClassTag

object MainApp extends App {

    util.PrintUtil.printChapterTitle(19, "advanced types")
    println()
    util.PrintUtil.printKeyPoints("Singleton types are useful for method chaining and " +
        "methods with object parameters.",
        "A type projection includes inner class instances for all objects of an outer " +
        "class.",
        "A type alias gives a short name for type.",
        "Structural types are equivalent to duck typing.",
        "Existential types provide the formalism for wildcard parameters of generic " +
        "types.",
        "The cake pattern uses self types to implement a dependency injection mechanism.",
        "An abstract type must be made concrete in a subclass.",
        "A higher-kinded type has a type parameter that is itself a parameterized type.")


    class Document {

        private var _title: String = ""
        private var _author: String = ""

        def setTitle(title: String): this.type = {
            _title = title
            this
        }

        def setAuthor(author: String): this.type = {
            _author = author
            this
        }

        def getTitle(): String = _title
        def getAuthor(): String = _author
    }

    class Book extends Document  {

        private val chapters: ListBuffer[String] =
            new ListBuffer[String]()

        def addChapter(chapter: String): this.type = {
            chapters.+(chapter)
            this
        }
    }

    val book = new Book()
    book.setTitle("a").setAuthor("b").addChapter("c")


    object FirstName
    class Person {
        private var useNextArgAs: Any = null

        private var firstName: String = ""

        def set(obj: Any): this.type = {
            useNextArgAs = obj
            this
        }

        def to(obj: Any): Unit = {
            useNextArgAs match {
                case FirstName =>
                    firstName = obj.asInstanceOf[String]
                case _ =>
                    println("what?")
            }
        }

        def getFirstName(): String = firstName
    }

    val p = new Person
    p set FirstName to "abc"
    println(p.getFirstName())

    def appendLine(target: {def append(str: String): Any},  line: String): Unit = {
        target.append(line)
    }

    object A {
        def append(str: String): Any = {
            println(str)
            1
        }
    }

    appendLine(A, "a")

    trait Reader {
        type Contents
        def read(fileName: String): Contents
    }

    class StringReader extends Reader {
        override type Contents =  String
        override def read(fileName: String) = s"read $fileName"
    }

    class BinaryReader extends Reader {
        override type Contents = Array[Byte]

        override def read(fileName: String): Array[Byte] = s"read $fileName".getBytes
    }

    val reader: Reader = new BinaryReader
    println(reader.read("abc file"))

    type AS = String
    val asa : AS = "abc"
    println(asa)
    type X[A, B] = (A, B)
    val ab: (String, Int) = ("One", 1)
    println(ab)
    val cd: String X Int = ("One", 1)
    println(cd)

    trait Logger {
        def log(msg: String)
    }

    trait Auth {
        this: Logger =>
        def login(id: String, password: String): Boolean
    }

    class TheAuth extends Auth with Logger {
        def log(msg: String): Unit = {
            println(msg)
        }

        override def login(id: String, password: String): Boolean = {
            log(s"login with $id $password")
            "a".equals(id) && "b".equals(password)
        }
    }

    val tha = new TheAuth
    println(tha.login("a", "b"))

    trait Listener[E] {
        def occurred(e: E): Unit
    }

    class StringListener extends Listener[String] {
        override def occurred(e: String): Unit = println(s" string listener $e")
    }

    trait Source[E, L <: Listener[E]] {
        private val listeners = new ArrayBuffer[L]()

        def add(l: L) = listeners += l
        def remove(l: L) = listeners -= l
        def fire(e: E): Unit = {
            for (l <- listeners) l.occurred(e)
        }
    }


    class StringButton extends Source[String, StringListener] {
        def click(): Unit = {
            fire("a")
        }
    }

    class ActionEvent {
    }

    class ActionEventListener extends Listener[ActionEvent] {
        override def occurred(e: ActionEvent): Unit = println(s" action event listener $e")
    }

    class ActionEventButton extends Source[ActionEvent, ActionEventListener] {
        def click(): Unit = {
            fire(new ActionEvent)
        }
    }


    trait Event[S] {
        var source: S = _
    }

    trait ListenerSE[S, E <: Event[S]] {
        def occurred(e: E): Unit
    }

    trait SourceSE[S, E <: Event[S], L <: ListenerSE[S, E]] {
        this: S =>
        private val listeners = new ArrayBuffer[L]()

        def add(l: L) {listeners += l}
        def remove(l: L) {listeners -= l}
        def fire(e: E): Unit = {
            e.source = this
            for (l <- listeners) l.occurred(e)
        }
    }

    class ButtonSEEvent extends Event[ButtonSE] {
        override def toString: AS = s"button se event, source ${source.toString}"
    }
    trait ButtonSEListener extends ListenerSE[ButtonSE, ButtonSEEvent]
    class ButtonSE extends SourceSE[ButtonSE, ButtonSEEvent, ButtonSEListener] {
        def click(): Unit = {
            val be = new ButtonSEEvent
            fire(be)
        }
    }

    val bse = new ButtonSE
    bse.add(new ButtonSEListener {
        override def occurred(e: ButtonSEEvent): Unit = println(s"button se listener $e")
    })
    bse.click()


    trait MyIterable[E] {
        def iterator(): Iterator[E]
        def map[F](f: (E) => F): MyIterable[F]
    }

    abstract class MyBuffer[E] extends MyIterable[E] {
        override def iterator(): Iterator[E]
        override def map[F](f: (E) => F): MyIterable[F]
    }

    trait MyIterable2[E, C[_]] {
        def iterator(): Iterator[E]
        def build[F](): C[F]
        def map[F](f: (E) => F): C[F]
    }

    trait Container[F] {
        def += (e: F): Unit
    }

    class MyBuffer2[F] extends Container[F]{
        override def +=(e: F): Unit =  {
            println(s"+= $e")
        }

        override def toString: String = "nothing,ha ha."
    }

    class MyBuffer3[F] extends Container[F] {

        val ab = new ArrayBuffer[F]()

        override def +=(e: F): Unit = {
            ab += e
        }

        override def toString: String = ab.toString()
    }

    class MyRange2(val low: Int, val high: Int) extends MyIterable2[Int, MyBuffer2] {
        override def build[F](): MyBuffer2[F] = new MyBuffer2[F]()

        override def iterator(): Iterator[Int] = new Iterator[Int] {
            private var i = low

            override def hasNext: Boolean = i <= high;

            override def next(): Int = { i += 1; i-1}
        }

        override def map[F](f: (Int) => F): MyBuffer2[F] = {
            val result = build[F]()
            val it = iterator()
            while (it.hasNext) {
                val ff = f(it.next())
                result += ff
            }
            result
        }
    }

    val mr2 = new MyRange2(1,4)
    println(mr2.map(_.toString))

    trait MyIterable3[E, C[F] <: Container[F]] {
        def build[F](): C[F]
        def iterator(): Iterator[E]
        def map[F](f: (E) => F): C[F] = {
            val result = build[F]()
            val it = iterator()
            while (it.hasNext) {
                val ff = f(it.next())
                result += ff
            }
            result
        }
    }


    class MyRange3(val low: Int, val high: Int) extends MyIterable3[Int, MyBuffer3] {

        override def iterator(): Iterator[Int] = new Iterator[Int] {
            private var i = low

            override def hasNext: Boolean = i <= high;

            override def next(): Int = { i += 1; i-1}
        }

        override def build[F](): MyBuffer3[F] = new MyBuffer3[F]()
    }

    val mr = new MyRange3(1,3)
    println(mr.map(_.toString))

}
