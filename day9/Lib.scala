type Point = (Long, Long)

object Lib:

  /** Computes the largest possible area, given the 2D coordinates of the red
    * tiles.
    */
  def largestArea(redTiles: List[Point]): Long =
    redTiles
      .combinations(2)
      .map { tiles =>
        computeArea(tiles(0), tiles(1))
      }
      .max

  /** Performs a Graham scan for computing the complex hull of the red tiles. */
  def grahamScan(redTiles: List[Point]): List[Point] =
    val anchorPoint = redTiles.minBy(p => (p._2, p._1))
    val sortedTiles = redTiles
      .sortBy { tile =>
        val (ax, ay) = anchorPoint
        val (tx, ty) = tile
        math.atan2(ty - ay, tx - ax)
      }

    sortedTiles.foldLeft(List.empty[Point]) { (stack, point) =>
      processPoint(stack, point)
    }

  /** Computes the largest possible area, given the red-green constraints in
    * part 2 of the problem.
    */
  def largestRedGreenArea(
      redTiles: List[Point],
      slabIntervals: Map[Point, List[Point]]
  ): Long =
    redTiles
      .combinations(2)
      .filter { case List(tile1, tile2) =>
        val (x1, y1) = tile1
        val (x2, y2) = tile2
        val xMin = x1 min x2
        val xMax = x1 max x2
        val yMin = y1 min y2
        val yMax = y1 max y2

        val overlappingSlabs = slabIntervals.filter { case ((yLow, yHigh), _) =>
          yMin < yHigh && yMax > yLow
        }

        overlappingSlabs.forall { case (_, intervals) =>
          intervals.exists { case (xLo, xHi) =>
            xLo <= xMin && xMax <= xHi
          }
        }
      }
      .map { case List(tile1, tile2) => computeArea(tile1, tile2) }
      .max

  /** Processes a single point (tile), adding it to the convex hull if
    * necessary.
    */
  private def processPoint(
      stack: List[Point],
      point: Point
  ): List[Point] =
    stack match
      case a :: b :: tail if crossProduct(b, a, point) <= 0 =>
        // stack has 2+ elements AND right turn => pop
        processPoint(b :: tail, point)
      case _ => point :: stack // < 2 elements OR left turn => push

  /** Computes the area between two 2D points (tiles), assuming the represent
    * the opposite corners of a rectangle.
    */
  private def computeArea(tile1: Point, tile2: Point): Long =
    val (x1, y1) = tile1
    val (x2, y2) = tile2
    // +1 to include the borders
    ((x1 - x2).abs + 1) * ((y1 - y2).abs + 1)

  /** Computes the 2D cross-product between three points (tiles). */
  private def crossProduct(
      tile1: Point,
      tile2: Point,
      tile3: Point
  ): Long =
    val (x1, y1) = tile1
    val (x2, y2) = tile2
    val (x3, y3) = tile3
    (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)
