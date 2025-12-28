import Lib.grahamScan
import Lib.largestArea

@main def main(): Unit =
  val redTiles = scala.io.Source
    .fromFile("red_tiles.txt")
    .getLines()
    .map { line =>
      line.split(",").map(_.toLong) match
        case Array(x, y) => (x, y)
    }
    .toList

  val convexHull = grahamScan(redTiles)
  printf("[PHASE 1] Largest area: %d\n", largestArea(convexHull))
