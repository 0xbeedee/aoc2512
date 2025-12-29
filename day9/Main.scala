import Lib.grahamScan
import Lib.largestArea
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

  val verticalEdges = redTiles
    .zip(redTiles.tail :+ redTiles.head)
    .collect {
      case ((x1, y1), (x2, y2)) if (x1 == x2) =>
        (x1, Math.min(y1, y2), Math.max(y1, y2))
    }

  // vertices of the rectilinear polygon
  val criticalYs = redTiles.map(_._2).distinct.sorted

  val slabs = criticalYs.zip(criticalYs.tail)
  val slabIntervals = slabs.map { case (yLow, yHigh) =>
    val spanningXs = verticalEdges
      .filter { case (x, yMin, yMax) =>
        yMin < yHigh && yMax > yLow
      }
      .map(_._1)
      .sorted

    val intervals =
      spanningXs.grouped(2).toList.map { case List(a, b) => (a, b) }
    (yLow, yHigh) -> intervals
  }.toMap

  printf(
    "[PHASE 2] Largest red-green area: %d\n",
    largestRedGreenArea(redTiles, slabIntervals)
  )
