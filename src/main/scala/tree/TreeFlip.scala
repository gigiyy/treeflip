package tree

object TreeFlip {

  def main(args: Array[String]): Unit = {

  }

  def buildTree(map: Map[Pos, Node]): Node = {
    ???
  }

  def parseInput(input: Seq[String]): Map[Pos, Node] =
    input.filter(s => s.startsWith("[[") && s.endsWith("]]")).map { str =>
      val content = str.slice(2, str.length - 2)
      content.split(",").map(_.trim.toInt)
    }.zipWithIndex.flatMap {
      case (ints, row) =>
        parseRow(ints).map(p =>
          (Pos(row, p._1), p._2)
        )
    }.toMap

  def parseRow(ints: Array[Int]): Seq[(Int, Node)] =
    ints.zipWithIndex.filter(_._1 != 0).map(p => (p._2, Node(p._1)))


}


case class Node(id: Int, var left: Option[Node] = None, var right: Option[Node] = None) {

}

object Node {
  def apply(id: Int) = new Node(id)
}

case class Pos(row: Int, col: Int)
