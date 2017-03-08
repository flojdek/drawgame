package com.typesane.game

import com.typesane.{graph => g} // Let's keep this clean.

object Game {

  type CanvasOp = g.Canvas => Unit

  def doCanvasOps(canvas: Option[g.Canvas], canvasOps: List[CanvasOp]): Unit = canvas match {
    case None => println("error: no canvas to render, first create canvas")
    case Some(c) => canvasOps.foreach(_(c))
  }

  def main(args: Array[String]): Unit = {

    // Current canvas.
    var canvas: Option[g.Canvas] = None
    // Operations to be performed on the canvas after each action.
    var canvasOps: List[CanvasOp] = List.empty

    val sc = new java.util.Scanner(System.in)
    var stop = false
    while (!stop) {
      try {
        print("enter command: ")
        sc.next() match {
          case "C" =>
            val width = sc.nextInt()
            val height = sc.nextInt()
            canvas = Some(new g.Canvas(width, height))
            canvasOps = List.empty
          case "B" =>
            val start = g.Point(sc.nextInt(), sc.nextInt())
            val fill = sc.next().charAt(0)
            canvasOps = List(_.drawFill(g.Fill(start, fill)))
          case "R" =>
            val upperLeft: g.Point = g.Point(sc.nextInt(), sc.nextInt())
            val lowerRight: g.Point = g.Point(sc.nextInt(), sc.nextInt())
            canvasOps = List(_.drawRectangle(g.Rectangle(upperLeft, lowerRight)))
          case "L" =>
            val start: g.Point = g.Point(sc.nextInt(), sc.nextInt())
            val stop: g.Point = g.Point(sc.nextInt(), sc.nextInt())
            canvasOps = List(_.drawLine(g.Line(start, stop)))
          case "Q" =>
            stop = true
          case _ =>
            println("error: unknown command")
        }
        // Always render at the end.
        if (!stop) doCanvasOps(canvas, canvasOps :+ {c: g.Canvas => c.render()})
      }
      catch {
        case e: Exception =>
          // e.printStackTrace() - Turn on for debugging.
          println(s"error: ${e.toString}")
      }
    } // end of while

  } // end of main

}
