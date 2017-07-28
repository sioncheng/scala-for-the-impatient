package ch7

package com {
    package host {

        private[host] object Utils {
            def percentOf(value: Double, rate: Double) = value * rate / 100
        }

        package impatient {
            class Employee(private var salary: Double) {

                def giveRaise(rate: _root_.scala.Double): Unit = {
                    //package path is absolute for scala Double
                    salary += Utils.percentOf(salary, rate)
                    //package path is relative for Utils
                }

                def currentSalary = salary
            }
        }
    }
}

object MainApp extends App {

    util.PrintUtil.printChapterTitle(7, "package and import")
    println()
    util.PrintUtil.printKeyPoints("Packages nest just like inner classes.",
        "Package path are not absolute.",
        "A chain x.y.z in a package clause leaves the intermediate packages " +
        "x and x.y invisible.",
        "Package statement without braces at the top of the file extend to " +
        "the entire file.",
        "A package object can hold functions and variables.",
        "Import statements can be anywhere.",
        "Import statements can rename and hide members.",
        "java.lang, scala, and Predef are always imported.")


    val employee = new com.host.impatient.Employee(1000)
    //println(com.host.Utils.percentOf(10, 50))
    employee.giveRaise(7)
    println(employee.currentSalary)

    import java.awt.{Color, Font}
    println(Color.WHITE)
    println(Font.BOLD)
}
