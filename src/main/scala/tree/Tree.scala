package tree

import scala.collection.immutable.SortedSet

object Tree {

  case class Node(id: Int, var left: Option[Node] = None, var right: Option[Node] = None)

  object Node {
    def apply(id: Int) = new Node(id)
  }

  case class Pos(row: Int, col: Int) {
    def possibleChildren: Set[Pos] = Set(leftChild, rightChild)

    def leftChild = Pos(row + 1, col - 1)

    def rightChild = Pos(row + 1, col + 1)
  }

  object Pos {
    implicit val order: Ordering[Pos] = Ordering.by(p => (p.row, p.col))
  }

  def flipTree(root: Node): Node = {
    def flip(node: Option[Node]): Option[Node] = {
      node match {
        case None => None
        case Some(nd) =>
          val next = Node(nd.id)
          next.left = flip(nd.right)
          next.right = flip(nd.left)
          Some(next)
      }
    }

    flip(Some(root)).get
  }

  def buildTree(map: Map[Pos, Node]): Node = {
    val ps = map.keySet
    val rootRow = ps.filter(_.row == 0)
    require(rootRow.size == 1, "there should be only on node on row 0")
    val rootPos = rootRow.head
    val root = map(rootPos)

    def nodeOfRow(row: Int): SortedSet[Pos] = SortedSet.empty[Pos] ++ ps.filter(_.row == row)

    var seem = Set(rootPos)
    var curRow = 0
    val cur = collection.mutable.Queue(rootPos)
    var next = nodeOfRow(curRow + 1)
    while (cur.nonEmpty) {
      val parentPos = cur.dequeue

      val parent = map(parentPos)
      val childrenPos = next.intersect(parentPos.possibleChildren).filter(!seem.contains(_))
      childrenPos.foreach { p =>
        seem += p
        if (p == parentPos.leftChild) parent.left = Some(map(p))
        else if (p == parentPos.rightChild) parent.right = Some(map(p))
      }
      if (cur.isEmpty) {
        cur ++= next
        curRow += 1
        next = nodeOfRow(curRow + 1)
      }
    }
    root
  }

  def validateInput(input: Seq[String]): Seq[String] =
    input.filter(s => s.startsWith("[[") && s.endsWith("]]") && s.length > 4)

  def parseInput(input: Seq[String]): Map[Pos, Node] =
    input.map { str =>
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


