package com.typesane.graph

class Canvas(width: Int, height: Int) {
  require(width >=1 && height >= 1)

  // Initialization.
  private val grid: CharGrid = new CharGrid(width + 2, height + 2) // For frame +2.
  drawFrame()

  // --== PUBLIC ==--

  def render() = grid.render()

  def drawLine(l: Line): Unit = {
    checkPointInCanvas(l.start)
    checkPointInCanvas(l.stop)
    drawLineNoCheck(l)
  }

  def drawRectangle(r: Rectangle): Unit = {
    checkPointInCanvas(r.upperLeft)
    checkPointInCanvas(r.bottomRight)
    drawRectangleNoCheck(r)
  }

  def drawFill(fill: Fill): Unit = {

    checkPointInCanvas(fill.start)

    // Auxiliary function doing recursive filling.
    def drawFillAux(start: Point, fl: Fill): Unit = {
      val startFill: Char = grid.get(start)
      if (startFill != fl.target) return
      if (startFill == fl.replacement) return
      grid.put(start, fl.replacement)
      drawFillAux(Point(start.x, start.y + 1), fl)
      drawFillAux(Point(start.x + 1, start.y), fl)
      drawFillAux(Point(start.x - 1, start.y), fl)
      drawFillAux(Point(start.x, start.y - 1), fl)
    }

    drawFillAux(fill.start, fill)

  }

  override def toString: String = grid.toString

  // --== PRIVATE ==--

  private def drawLineNoCheck(l: Line): Unit = {

    // Draw the line.
    if (l.start.x == l.stop.x) {
      // Vertical line.
      val min = Math.min(l.stop.y, l.start.y)
      val max = Math.max(l.stop.y, l.start.y)
      for (y <- min to max) {
        grid.put(Point(l.start.x, y), l.fill)
      }
    }
    else if (l.start.y == l.stop.y) {
      // Horizontal line.
      val min = Math.min(l.stop.x, l.start.x)
      val max = Math.max(l.stop.x, l.start.x)
      for (x <- min to max) {
        grid.put(Point(x, l.start.y), l.fill)
      }
    }
    else {
      throw new RuntimeException("error: drawing not straight line not supported")
    }

  }

  private def drawLinesNoCheck(ls: List[Line]): Unit = ls.foreach(drawLineNoCheck)

  private def drawRectangleNoCheck(r: Rectangle): Unit = {
    //           r.upperLeft            --- (r.bottomRight.x, r.upperLeft.y)
    //               |                                       |
    //               |                                       |
    // (r.upperLeft.x, r.bottomRight.y) ---             r.bottomRight
    drawLinesNoCheck(List(
      Line(r.upperLeft, Point(r.bottomRight.x, r.upperLeft.y)), // Top.
      Line(r.upperLeft, Point(r.upperLeft.x, r.bottomRight.y)), // Left.
      Line(Point(r.upperLeft.x, r.bottomRight.y), r.bottomRight), // Bottom.
      Line(Point(r.bottomRight.x, r.upperLeft.y), r.bottomRight) // Right.
    ))
  }

  private def drawFrame(): Unit = {
    drawLinesNoCheck(List(
      Line(Point(0, 0), Point(width + 1, 0), '-'), // Top
      Line(Point(0, 1), Point(0, height), '|'), // Left.
      Line(Point(0, height + 1), Point(width + 1, height + 1), '-'), // Bottom.
      Line(Point(width + 1, 1), Point(width + 1, height), '|') // Right.
    ))
  }

  private def checkPointInCanvas(p: Point): Unit =
    if (!(p.x >= 1 && p.x <= width && p.y >= 1 && p.y <= height)) {
      throw new RuntimeException(s"error: point not within canvas, p='$p'")
    }

}
