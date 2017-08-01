package ch15

import java.io.Serializable

import scala.annotation.tailrec

object MainApp extends App {

    util.PrintUtil.printChapterTitle(15, "Annotation")
    println()
    util.PrintUtil.printKeyPoints("You can annotate classes, methods, " +
        "fields, local variables, parameters, expressions, type parameters, "+
        "and types.",
        "With expressions and types, the annotation follows the annotated " +
        "item.",
        "Annotations have the form @Annotation, @Annotation(value), or " +
        "@Annotation(name1 = value1, name2 = value2, ..).",
        "@volatile, @transient, @strictfp, and @native generate the equivalent " +
        "java modifiers.",
        "Use @throws to generate java-compatible throws specifications.",
        "The @tailrec annotation lets you verify that a recursive function uses " +
        "tail call optimization.",
        "The assert function takes advantage of the @elidable annotation.",
        "Use the @deprecated annotation to mark deprecated features."
    )

    @SerialVersionUID(1L)
    class Credentials(var username: String, var password: String) extends Serializable

    class Book {
        @throws(classOf[IllegalArgumentException]) def read(){}
    }

    @tailrec
    def sum(xs: Seq[Int], acc: Int): Int = xs match {
        case Nil => acc
        case head :: tail => sum(tail, acc + head)
    }

}
