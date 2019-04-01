package tree

import scala.io.Source

object App {

  import Tree._
  import Printer._

  def main(args: Array[String]): Unit = {
    val input = validateInput(readInput())
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
  }

  def printTree(root: Node): Unit = {
    val ((height, width), elements) = generateElements(root)
    elementsToString(elements, height, width).foreach(println)
  }

  import scala.io.StdIn

  def readInput(): Seq[String] = {
    var ok = true
    var seq = Seq.empty[String]
    while (ok) {
      val ln = StdIn.readLine
      ok = ln != null && ln.nonEmpty
      if (ok) {
        seq :+= ln
      }
    }
    seq
  }

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
