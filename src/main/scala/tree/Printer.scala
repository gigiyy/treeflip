package tree

import tree.Tree.Node

object Printer {
  def elementsToString(elements: Seq[Element], height: Int, width: Int): Seq[String] = {
    for (row <- 0 until height) yield {
      val array = Array.fill(width)(' ')
      elements.filter(_.pos.row == row).foreach { e =>
        var col = 0
        for (c <- e.value) {
          array.update(e.pos.col + col, c)
          col += 1
        }
      }
      array.mkString("")
    }
  }

  def generateElements(root: Node): ((Int, Int), Seq[Element]) = {
    def mapToElement(node: Option[Node], at: Pos): Seq[Element] = {
      node match {
        case None => Seq.empty
        case Some(nd) =>
          var result = Seq.empty[Element]
          val e = Element(nd.id.toString, at)
          result :+= e
          if (nd.left.nonEmpty) result :+= e.leftTrunk()
          if (nd.right.nonEmpty) result :+= e.rightTrunk()
          result ++ mapToElement(nd.left, at.left.left) ++ mapToElement(nd.right, at.right.right)
      }
    }

    val allElements = mapToElement(Some(root), Pos(0, 0))

    val (minRow, maxRow, minCol, maxCol) = allElements.foldLeft((0, 0, 0, 0)) { (current, element) =>
      val (minR, maxR, minC, maxC) = current
      val row = element.pos.row
      val col = element.pos.col
      val len = element.value.length
      (if (row < minR) row else minR, if (row > maxR) row else maxR,
        if (col < minC) col else minC, if (col + len - 1 > maxC) col + len - 1 else maxC)
    }
    assert(minRow == 0)

    val normalized = allElements.map(e => Element(e.value, Pos(e.pos.row, e.pos.col + Math.abs(minCol))))
    ((maxRow + 1, maxCol + math.abs(minCol) + 1), normalized)
  }

  case class Element(value: String, pos: Pos) {
    def leftTrunk() = Element("/", pos.left)

    def rightTrunk() = Element("\\", pos.right)
  }

  case class Pos(row: Int, col: Int) {
    def left: Pos = Pos(row + 1, col - 1)

    def right: Pos = Pos(row + 1, col + 1)
  }

}
