package com.typesane.graph

class CharGrid(width: Int, height: Int, fill: Char = ' ') {
  require(width >= 1 && height >= 1)

  // Initialization.
  private val rep: Array[Array[Char]] = Array.fill(height, width)(fill)

  def put(p: Point, v: Char): Char = {
    if (p.x < 0 || p.x >=width || p.y < 0 || p.y >= height)
      throw new RuntimeException(s"error: point out of bounds, p=$p")
    rep(p.y)(p.x) = v; v
  }

  def get(p: Point): Char = rep(p.y)(p.x)

  def foreach(f: Char => Unit): Unit = rep.foreach(_.foreach(f))

  def render(): Unit = rep.foreach(row => println(row.mkString))

  override def toString: String = rep.map(_.mkString).mkString("@")

}

