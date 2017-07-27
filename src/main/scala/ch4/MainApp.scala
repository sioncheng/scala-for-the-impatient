package ch4

object MainApp extends App {

    util.PrintUtil.printChapterTitle(4, "Maps and Tuples")
    println()
    util.PrintUtil.printKeyPoints("Scala has a pleasant syntax for " +
        "creating, querying and traversing maps.",
        "You need to choose between mutable and immutable maps.",
        "By default, you get a hash map, but you can also get a tree map.",
        "You can easily convert between Scala and Java maps.",
        "Tuples are useful for aggregating values.")

    val scores = Map("Alice" -> 10, "Bob" -> 8, "Cindy" -> 9)
    val scores2 = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 8, "Cindy" -> 9)
    val scores3 = Map(("Alice", 10), ("Bob", 8), ("Cindy", 9))
    println(scores3("Bob"))
    println(scores2("Bob"))
    println(scores("Bob"))
    println(scores3.getOrElse("John", 0))
    scores2("Bob") = 10
    println(scores2("Bob"))
    scores2 += ("John" -> 9)
    println(scores2.getOrElse("John", 0))

    def printMap[K, V](map: Map[K,V]): Unit = {
        for ((k, v) <- map) {
            print(k.toString() + " -> " + v.toString() + " , ")
        }
        println()
    }

    def printMap[K, V](map: scala.collection.mutable.Map[K,V]): Unit = {
        for ((k, v) <- map) {
            print(k.toString() + " -> " + v.toString() + " , ")
        }
        println()
    }

    printMap(scores3)
    printMap(scores2)

    val sortedScoresMap = scala.collection.mutable.SortedMap("Alice" -> 10,
        "John" -> 10,
        "Bob" -> 8,
        "Cindy" -> 9)
    printMap(sortedScoresMap)
    sortedScoresMap += ("Bill" -> 7)
    printMap(sortedScoresMap)

    val jmap = new java.util.TreeMap[String, Int]()
    jmap.put("One", 1)
    jmap.put("Two", 2)
    val smap = scala.collection.JavaConverters.mapAsScalaMap(jmap)
    printMap(smap)

    val t = (1, "One", "一")
    print(f"${t._1} ${t._2} ${t._3} %n")

    val (first, second, third) = (1,2,3)
    print(f"$first $second $third %n")

    val symbols = Array("<", "-", ">")
    val counts = Array(2, 10, 2)
    val pairs = symbols.zip(counts)
    for ((s, n) <- pairs) print(s * n)
    println()
}
