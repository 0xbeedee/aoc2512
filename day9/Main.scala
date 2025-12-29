import Lib.grahamScan
import Lib.largestArea
import Lib.computeSlabIntervals
import Lib.largestRedGreenArea

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

  val slabIntervals = computeSlabIntervals(redTiles)
  printf(
    "[PHASE 2] Largest red-green area: %d\n",
    largestRedGreenArea(redTiles, slabIntervals)
  )
