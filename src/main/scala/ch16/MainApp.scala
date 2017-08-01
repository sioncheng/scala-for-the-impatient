package ch16

object MainApp extends App {

    util.PrintUtil.printChapterTitle(16, "xml processing")
    println()
    util.PrintUtil.printKeyPoints("xml literals <like>this</like> are of type NodeSeq.",
        "You can embed scala code inside xml literals.",
        "The child property of a Node yields the child nodes.",
        "The attribute property of a Node yields a MetaData object containing "+
        "the attributes.",
        "The \\ and \\\\ operators carry out XPath-like matches.",
        "You can match node patterns with xml literals in case clauses.",
        "Use the RuleTransformer with RewriteRule instances to transform " +
        "descendants of of a node.",
        "The xml object interfaces with java xml methods for loading and saving.",
        "The ConstructingParser is an alternate parser that preserves comments "+
        "and CDATA sections.")
    println()

    val doc = <html><head><title></title></head><body></body></html>

    println(doc)

    for (n <- doc.child) {
        println(n)
    }

    val anchor = <a href="http://www.google.com" alt="San Jos &quote; Hello">google</a>
    val url = anchor.attributes("href")
    println(url)
    println(anchor.attributes("alt"))
    for(attr <- anchor.attributes) println(attr.key , attr.value)

    val items = List("one", "two")
    val ul = <ul><li>{items(0)}</li><li>{items(1)}</li></ul>
    println(ul)

    println(<ul>{for (i <- items) yield <li>{i}</li> }</ul>)

    val js = <script language="javascript"><![CDATA[alert("scala")]]></script>
    println(js)

    val list = <dl><dt>java</dt><dt>scala</dt></dl>
    println(list \ "dt")

    <img>ab</img> match {
        case <img>{c}</img> =>
            println(c)
        case _ => println("what?")
    }

    val list2 = <ul><li>Fred</li><li>Wilma</li></ul>
    println(list2.copy(label = "ol"))

    import scala.xml.transform._
    import scala.xml._
    val rule1 = new RewriteRule {
        override def transform(n: Node) = n match {
            case e @ <ul>{_*}</ul> => e.asInstanceOf[Elem].copy(label = "ol")
            case _ => n
        }
    }
    println(new RuleTransformer(rule1).transform(list2))

    val html = <html xmlns="http://www.w3.org/1999/xhtml"
        xmlns:svg="http://www.w3.org/2000/svg">
        <head><title></title></head>
        <body>
            <div>html</div>
            <svg:svg with="100" height="100"></svg:svg>
        </body>
    </html>

    println(html.prefix)
    println((html \ "body" \ "svg")(0).prefix)
}
