@main def main(): Unit =
  val red_tiles = scala.io.Source
    .fromFile("red_tiles.txt")
    .getLines()
    .map { line =>
      val parts = line.split(",").map(_.toLong)
      val Array(x, y) = parts
      (x, y)
    }
    .toList

  val largestArea = Lib.largest_area(red_tiles)
  printf("[PHASE 1] Largest area: %d\n", largestArea)
