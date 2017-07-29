package ch9

object MainApp extends App {

    util.PrintUtil.printChapterTitle(9, "Files and Regular Expression")
    println()
    util.PrintUtil.printKeyPoints("Source.fromFile(...).getLines.toArray yields all lines of a file." ,
        "Source.fromFile(...).mkString yields the file contents as a string." ,
        "To convert a string into a number, use the toInt or toDouble method." ,
        "Use the java PrintWriter to write text files." ,
        "\"regex\".r is a regex object." ,
        "Use \"\"\"...\"\"\" if your regular expression contains backslashes or quotes." ,
        "If a regular pattern has groups, you can extract their contents using the syntax "
        + "for (regex(var1, ..., varn) <- string)" )


    import scala.io.Source
    val path = MainApp.getClass.getResource("../README.txt").getPath
    val source = Source.fromFile(path)
    println(source.mkString)
    source.close()

    val source2 = Source.fromFile(path)
    val iterator = source2.buffered
    while (iterator.hasNext) {
        if (iterator.head.toInt > 100) {
            print(iterator.head)
            print(' ')
        }
        iterator.next()
    }
    println()
    source2.close()

    /*
    val source3 = Source.fromURL("https://github.com", "UTF-8")
    for (line <- source3.getLines()) println(line)
    source3.close()
    */

    import scala.sys.process._
    println("ls -a".!)

    ("ls -a " #| "grep s").!

    val numberPattern = "[0-9]+".r
    println(numberPattern.regex)
    for (matchedNumber <- numberPattern.findAllIn("123 abc 456ksjdf")){
        println(matchedNumber)
    }

    val witheSpaceNumberPattern = """\s+[0-9]+\s+""".r
    for (matchedNumber <- witheSpaceNumberPattern.findAllIn(" 123 abc 456 ksjdf")) {
        println(s"[$matchedNumber]")
    }

    val numberItemPattern = "([0-9]+) ([a-z]+)".r
    for (m <- numberItemPattern.findAllMatchIn("1 apples, 2 oranges")) {
        println(m.group(1), m.group(2))
    }
}