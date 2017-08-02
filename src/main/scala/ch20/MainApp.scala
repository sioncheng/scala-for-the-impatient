package ch20

object MainApp extends App {

    util.PrintUtil.printChapterTitle(20, "parsing")
    println()
    util.PrintUtil.printKeyPoints("Alternatives, concatenation, options, and repetitions " +
        "in a grammar turn into |,~,opt, and rep in Scala combinator parsers.",
        "With RegexParsers, literal strings and regular expressions match tokens.",
        "Use ^^ to process parse results.",
        "Use pattern matching in a function supplied to ^^ to take apart ~ results.",
        "Use ~> and <~ to discard tokens that are no longer needed after matching.",
        "The repsep combinator handles the common case of repeated items with a separator.",
        "A token-based parser is useful for parsing language with reserved words and " +
        "operators. Be prepared to define your own lexer.",
        "Parsers are functions that consume a reader and yield a parser result: success, " +
        "failure, or error.",
        "For a practical parser, you need to implement robust error reporting.",
        "Thanks to operator symbols, implicit conversions, and pattern matching, the " +
        "parser combinator library makes parser writing easy for anyone who understands "+
        "context-free grammars. Even if you don't feel the urge to write your own parsers, " +
        "you may find this an interesting case study for an effective domain-specific "+
        "language.")
    println()

}