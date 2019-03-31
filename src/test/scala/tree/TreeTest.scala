package tree

import org.scalatest._

class TreeTest extends FunSuite with Matchers with
  OptionValues with Inside with Inspectors {

  import Tree._

  test("array of ints to tree node") {
    assert(parseRow(Array(0, 0, 1, 0, 0)) === Seq((2, Node(1))))
  }
  val input: Seq[String] = Seq(
    "0, 0, 1, 0, 0",
    "0, 2, 0, 3, 0",
    "4, 0, 5, 0, 0",
    "0, 6, 0, 7, 0"
  ).map(s => s"[[$s]]")

  test("parse input to map") {
    val map = parseInput(input)
    assert(map === Map(
      Pos(0, 2) -> Node(1),
      Pos(1, 1) -> Node(2), Pos(1, 3) -> Node(3),
      Pos(2, 0) -> Node(4), Pos(2, 2) -> Node(5),
      Pos(3, 1) -> Node(6), Pos(3, 3) -> Node(7)
    ))
  }

  test("build tree from map") {
    val map = parseInput(input)
    val root = buildTree(map)
    assert(root.id === 1)
    assert(root.left.map(_.id) === Some(2))
    assert(root.right.map(_.id) === Some(3))
    assert(root.left.flatMap(_.left.map(_.id)) === Some(4))
    assert(root.left.flatMap(_.right.map(_.id)) === Some(5))
    assert(root.left.flatMap(_.left.flatMap(_.right.map(_.id))) === Some(6))
    assert(root.left.flatMap(_.right.flatMap(_.right.map(_.id))) === Some(7))
  }

  import TreeFlip._

  test("print tree") {
    val root = buildTree(parseInput(input))
    val ((height, width), elements) = generateElements(root)
    printElements(elements, height, width)
  }
}
