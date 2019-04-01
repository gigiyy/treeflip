package tree

import java.io.InputStreamReader

import scala.io.Source
import scala.util.Try

object App {

  import Tree._
  import Printer._

  def main(args: Array[String]): Unit = {

    import scala.sys.process._

    if (args.isEmpty) {
      println("please specify the file to load")
    } else {
      val name = args.head
      val bs = Source.fromFile(name)
      val lines = bs.getLines().toSeq
      val input = validateInput(lines)
      if (input.isEmpty) {
        println("No valid input. please try again.")
      } else {
        println("Here is what we have from input:")
        input.foreach(println)

        val root = buildTree(parseInput(input))
        var ok = true
        while (ok) {
          getUserAction() match {
            case Print => printTree(root)
            case Flip => printTree(flipTree(root))
            case Exit => ok = false
          }
        }
      }
      bs.close()
    }
  }

  def printTree(root: Node): Unit = {
    val ((height, width), elements) = generateElements(root)
    elementsToString(elements, height, width).foreach(println)
  }

  import scala.io.StdIn

  def readInput(): Seq[String] = Source.stdin.getLines().toSeq

  def getUserAction(): Action = {
    println(
      s"""
         |Please choose what to do next
         | 1. print the tree
         | 2. flip tree
         | 3. exit
         | your input: """.stripMargin)
    StdIn.readInt() match {
      case 1 => Print
      case 2 => Flip
      case 3 => Exit
    }
  }

  sealed trait Action

  final case object Print extends Action

  final case object Flip extends Action

  final case object Exit extends Action

}
