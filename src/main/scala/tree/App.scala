package tree

import scala.io.Source

object App {

  import Printer._
  import Tree._

  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      println("please specify the file to load")
    } else {
      val bs = Source.fromFile(args.head)
      val input = validateInput(bs.getLines().toSeq)
      if (input.isEmpty) {
        println("No valid input. please try again.")
      } else {
        println("Here is what we have loaded:")
        input.foreach(println)

        val root = buildTree(parseInput(input))
        var ok = true
        while (ok) {
          getUserAction match {
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
    elementsToStringSeq(elements, height, width).foreach(println)
  }

  def getUserAction: Action = {
    import scala.io.StdIn
    print(
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
