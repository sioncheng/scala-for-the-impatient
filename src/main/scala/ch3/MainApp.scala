package ch3



object MainApp extends App {

    util.PrintUtil.printChapterTitle(3, "work with array")
    util.PrintUtil.printKeyPoints("Use an Array if the length is fixed, " +
        " and an ArrayBuffer if the length can vary." ,
        "Don't use new when supplying initial values." ,
        "Use () to access elements.",
        "Use for (elem <- arr) to traverse the elements.",
        "Use for (elem <- arr if ...) ... yield ... to transform into a new array.",
        "Scala and Java arrays are interoperable; with ArrayBuffer, use scala." +
        "collection.JavaConversions.")
    println()

    val numbers = new Array[Int](10)
    for (num <- numbers) println(num)
    for (i <- 0 to 9) {
        numbers(i) = i * i
    }
    for (num <- numbers) println(num)

    val s = Array("Hello", "World")
    for (se <- s) println(se)
    s(0) = "Goodbye"
    for (se <- s) println(se)

    import scala.collection.mutable.ArrayBuffer
    val b = new ArrayBuffer[Int]()
    b += 1
    b += (2,3,4,5)
    for (be <- b) println(be)
    b ++= ArrayBuffer[Int](6,7,8,9)
    for (be <- b) println(be)
    b.insert(0, 0)
    for (be <- b) println(be)
    b.remove(b.length - 1)
    for (be <- b) println(be)

    val sb = s.toBuffer
    sb += "Hello"
    sb ++= ArrayBuffer("Scala")
    for (se <- sb) println(se)

    val ba = b.toArray
    for (i <- 0 until ba.length) println(ba(i))

    val ba3 = for (bae <- ba) yield bae * 3
    println(ba3)
    for (ba3e <- ba3) println(ba3e)

    val ba4 = for (bae <- ba if bae % 2 == 0) yield bae * 4
    for (ba4e <- ba4) println(ba4e)

    for (ba4e <- ba.filter(_ % 2 == 0).map(x => 4 * x)) println(ba4e)

    println(ba.sum)
    println(ba.max)
    for(bae <- ba.reverse.sorted) println(bae)

    val matrix = Array.ofDim[Int](4, 4)
    for (i <- 0 until 4; j <- 0 until 4) {
        matrix(i)(j) = i * j
        println(matrix(i)(j))
    }

    val matrix2 = new Array[Array[Int]](4)
    for (i <- matrix2.indices) matrix2(i) = new Array[Int](4)
    for (i <- matrix2.indices; j <- matrix2(i).indices) {
        matrix2(i)(j) = i + j
        println(matrix2(i)(j))
    }

    val a = Array("Mary", "a", "had", "lamb", "little")
    val n = java.util.Arrays.binarySearch(a.asInstanceOf[Array[Object]], "lamb")
    println(n)

    import scala.collection.JavaConverters.bufferAsJavaList
    import scala.collection.mutable.ArrayBuffer
    val command = ArrayBuffer[String]("ls", "-al", ".")
    val pb = new ProcessBuilder(bufferAsJavaList(command)) //scala array buffer to java list
    import scala.collection.JavaConverters.asScalaBuffer
    import scala.collection.mutable.Buffer
    val cmd: Buffer[String] = asScalaBuffer(pb.command()) // java list as scala array buffer
    for (c <- cmd) println(c)

}
