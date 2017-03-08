package com.typesane.graph

case class Fill(
  start: Point,
  replacement: Char = 'o',
  target: Char = ' ' // The target color/character we're actually replacing.
)
