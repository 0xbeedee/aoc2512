import Lib.graham_scan
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

  val convex_hull = Lib.graham_scan(red_tiles)
  val largestArea = Lib.largest_area(convex_hull)
  printf("[PHASE 1] Largest area: %d\n", largestArea)
