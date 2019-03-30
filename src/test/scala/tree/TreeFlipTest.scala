package tree

import org.scalatest._

class TreeFlipTest extends FunSuite with Matchers with
  OptionValues with Inside with Inspectors {

  import TreeFlip._

  test("array of ints to tree node") {
    assert(parseRow(Array(0, 0, 1, 0, 0)) === Seq((2, Node(1))))
  }

  test("parse input to map") {
    val input = Seq(
      "0, 0, 1, 0, 0",
      "0, 2, 0, 3, 0",
      "4, 0, 5, 0, 0",
      "0, 6, 0, 7, 0"
    ).map(s => s"[[$s]]")
    val map = parseInput(input)
    assert(map === Map(
      Pos(0, 2) -> Node(1),
      Pos(1, 1) -> Node(2), Pos(1, 3) -> Node(3),
      Pos(2, 0) -> Node(4), Pos(2, 2) -> Node(5),
      Pos(3, 1) -> Node(6), Pos(3, 3) -> Node(7)
    ))
  }
}
