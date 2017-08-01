package ch18

object MainApp extends App {

    util.PrintUtil.printChapterTitle(18, "type parameters")
    println()
    util.PrintUtil.printKeyPoints("Classes, traits, methods, and functions " +
        "can have type parameters.",
        "Place the type parameters after the name, enclosed in square brackets.",
        "Type bounds have the form T <: UpperBound, T >: LowerBound, T :" +
        "ContextBound.",
        "You can restrict a method with a type constraint such as (" +
        "implicit ev: T <:< UpperBound).",
        "Use +T(covariance) to indicate that a generic type's subtype " +
        "relationship is in the same direction as the parameter T, or -T" +
         "(contravariance) to indicate the reverse direction.",
        "Covariance is appropriate for parameters that denote outputs, such " +
        "as elements in an immutable collection.",
        "Contravariance is appropriate for parameters that denote inputs, such " +
        "as function arguments."
    )

    case class Pair[T,S](first: T, second: S)
    val p1 = new Pair(1, 1.0)
    val p2 = new Pair(1, "1.0")
    println(p1, p2)

    def getMiddle[T](a: Array[T]) = a(a.length / 2)
    println(getMiddle(Array(1,2,3)))

    //upper bound Comparable[T]
    def min[T <: Comparable[T]](a: T, b: T): T = {
        if (a.compareTo(b) < 0) a else b
    }
    println(min[Integer](new Integer(1),new Integer(2)))

    class MyTuple[T](val first: T, val second: T) {
        def replaceFirst[R >: T](newFirst: R): MyTuple[R] = new MyTuple[R](newFirst, second)

        override def toString: String = s"$first $second"
    }

    class Person {}
    class Student extends Person{}

    val myTuple1 = new MyTuple[Student](new Student(), new Student())
    val myTuple2 = myTuple1.replaceFirst(new Person)
    println(myTuple1, myTuple2)

    //view bound Comparable[T]
    def min2[T <% Comparable[T]](a: T, b: T): T = {
        if (a.compareTo(b) < 0) a else b
    }
    println(min2(1,2))

    //type constraint Comparable[T]
    def min3[T](a: T, b: T)(implicit ts: T=>Comparable[T]): T = {
        if (a.compareTo(b) < 0) a else b
    }
    println(min3(1,2))

    //context bound Ordering
    def min4[T: Ordering](a: T, b: T)(implicit ord: Ordering[T]) : T = {
        if (ord.compare(a, b) < 0 ) a else b
    }

    println(min4(1,2))

    //implicit evidence parameter
    def min5[T](a: T, b: T)(implicit ev: T <:< Comparable[T]): T = {
        if (a.compareTo(b) < 0) a else b
    }

    class MyTuple2[+T](val first:T, val second: T)
    println(new MyTuple2[Student](new Student, new Student))
    println(new MyTuple2[Person](new Student, new Student))
    println(new MyTuple2[Person](new Student, new Person))

    trait MyFunction1[-A, +R] {
        def call(a: A):R
    }

    class MyFunction1Impl extends MyFunction1[Person, Student] {
        def call(a: Person): Student = {
            println(a)
            new Student
        }
    }

    var person1: Person = null
    var student1: Student = null
    val f1 = new MyFunction1Impl
    person1 = f1.call(new Person)
    student1 = f1.call(new Person)
    println(person1, student1, f1.call(new Student))

    def process(people: List[_ <: Person]): Unit = {
        for(p <- people) println(p)
    }

    process(List(new Student, new Student))
}
