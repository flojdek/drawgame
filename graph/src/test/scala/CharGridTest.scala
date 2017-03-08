import com.typesane.graph.{CharGrid, Point}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class CharGridTest extends FunSuite {

  test("Dimensions check while creating") {
    an [IllegalArgumentException] should be thrownBy new CharGrid(0, 0)
    an [IllegalArgumentException] should be thrownBy new CharGrid(1, 0)
    an [IllegalArgumentException] should be thrownBy new CharGrid(0, 1)
    an [IllegalArgumentException] should be thrownBy new CharGrid(0, 10)
  }

  test("Dimensions and fill character are correct") {
    val cg3 = new CharGrid(2, 2, 'd')
    assert(cg3.toString == "dd@dd")
  }

  test("Vanilla grid should be filled with ' '") {
    val cg = new CharGrid(3, 3)
    cg.foreach(c => assert(c == ' '))
  }

  test("`get` returns what `put` puts") {
    val cg = new CharGrid(2, 2)
    cg.put(Point(0, 0), 'a')
    assert(cg.get(Point(0, 0)) == 'a')
  }

  test("Out of bounds point test") {
    val cg = new CharGrid(2, 2)
    cg.put(Point(0, 0), 'a')
    cg.put(Point(0, 1), 'a')
    cg.put(Point(1, 0), 'a')
    cg.put(Point(1, 1), 'a')
    an [RuntimeException] should be thrownBy cg.put(Point(100, 100), 'a')
  }

}
