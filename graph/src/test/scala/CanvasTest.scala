import com.typesane.graph._
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class CanvasTest extends FunSuite {

  test("Canvas dimensions") {
    an [IllegalArgumentException] should be thrownBy new Canvas(0, 0)
    an [IllegalArgumentException] should be thrownBy new Canvas(0, 1)
    an [IllegalArgumentException] should be thrownBy new Canvas(1, 0)
    an [IllegalArgumentException] should be thrownBy new Canvas(-1, 6)
  }

  test("Draw line") {
    // ----    ----
    // |  | => |  |
    // |  |    |xx|
    // ----    ----
    val canvas: Canvas = new Canvas(2, 2)
    canvas.drawLine(Line(Point(1, 2), Point(2, 2)))
    assert(canvas.toString == "----@|  |@|xx|@----")
  }

  test("Draw rectangle") {
    // -----    -----
    // |   | => |xxx|
    // |   |    |x x|
    // |   |    |xxx|
    // -----    -----
    val canvas: Canvas = new Canvas(3, 3)
    canvas.drawRectangle(Rectangle(Point(1, 1), Point(3, 3)))
    assert(canvas.toString == "-----@|xxx|@|x x|@|xxx|@-----")
  }

  test("Bucket fill") {
    // ------    ------    ------
    // |    | => |oooo| => |oooo|
    // | xxx|    |oxxx|    |oxxx|
    // | x x|    |ox x|    |oxbx|
    // | xxx|    |oxxx|    |oxxx|
    // ------    ------    ------
    val canvas: Canvas = new Canvas(4, 4)
    canvas.drawRectangle(Rectangle(Point(2, 2), Point(4, 4)))
    canvas.drawFill(Fill(Point(1, 1)))
    assert(canvas.toString == "------@|oooo|@|oxxx|@|ox x|@|oxxx|@------")
    canvas.drawFill(Fill(Point(3, 3), 'b'))
    assert(canvas.toString == "------@|oooo|@|oxxx|@|oxbx|@|oxxx|@------")
  }

}
