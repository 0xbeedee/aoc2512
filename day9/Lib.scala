/** A 2D coordinate (x, y). */
type Point = (Long, Long)

/** A horizontal strip of the plane between two y-values (yLow, yHigh).
  *
  * Within a slab, the "inside" x-intervals of the rectilinear polygon are
  * constant - they only change when crossing from one slab to another.
  */
type Slab = (Long, Long)

/** An x-interval (xLo, xHi) representing a horizontal segment where points
  * are inside the rectilinear polygon for a given slab.
  */
type Interval = (Long, Long)

/** A vertical edge of the rectilinear polygon.
  *
  * Consecutive red tiles that share the same x-coordinate form a vertical
  * edge. These edges determine entry/exit points when scanning horizontally
  * across the polygon.
  */
case class VerticalEdge(x: Long, yMin: Long, yMax: Long)

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

  /** Extracts vertical edges from the ordered list of red tiles.
    *
    * The red tiles form a closed rectilinear polygon where consecutive tiles
    * are connected by axis-aligned segments. This function identifies the
    * vertical segments by finding consecutive tile pairs that share the same
    * x-coordinate.
    *
    * The wrap-around (last tile to first tile) is included to close the
    * polygon.
    */
  def extractVerticalEdges(redTiles: List[Point]): List[VerticalEdge] =
    redTiles
      .zip(redTiles.tail :+ redTiles.head)
      .collect {
        case ((x1, y1), (x2, y2)) if x1 == x2 =>
          VerticalEdge(x1, y1 min y2, y1 max y2)
      }

  /** Computes the inside x-intervals for each horizontal slab of the polygon.
    *
    * This is a scanline decomposition: we divide the plane into horizontal
    * slabs at each distinct y-coordinate of the red tiles. Within each slab,
    * the polygon's interior has constant x-intervals.
    *
    * For each slab, we find which vertical edges span it (i.e., cross through
    * that y-range). Sorting these edges by x-coordinate and pairing them up
    * gives us the "inside" intervals: the first edge is an entry, the second
    * is an exit, and so on.
    *
    * Example: if a slab has vertical edges at x = 2, 5, 8, 10, then the inside
    * intervals are [(2, 5), (8, 10)].
    */
  def computeSlabIntervals(redTiles: List[Point]): Map[Slab, List[Interval]] =
    val verticalEdges = extractVerticalEdges(redTiles)
    val criticalYs = redTiles.map(_._2).distinct.sorted
    val slabs = criticalYs.zip(criticalYs.tail)

    slabs.map { case (yLow, yHigh) =>
      val spanningXs = verticalEdges
        .filter { edge =>
          edge.yMin < yHigh && edge.yMax > yLow
        }
        .map(_.x)
        .sorted

      val intervals =
        spanningXs.grouped(2).toList.map { case List(a, b) => (a, b) }

      (yLow, yHigh) -> intervals
    }.toMap

  /** Computes the largest rectangle area using only red and green tiles.
    *
    * A rectangle is valid if:
    *   1. Two opposite corners are red tiles
    *   2. The entire rectangle lies within the rectilinear polygon (red+green)
    *
    * To check containment, we verify that for every slab the rectangle spans,
    * its x-range [xMin, xMax] is fully contained within one of that slab's
    * inside intervals. If any slab fails this check, the rectangle extends
    * outside the polygon.
    *
    * Complexity: O(n^2) where n is the number of red tiles, since we check all
    * pairs and each containment check is O(slabs) = O(n).
    */
  def largestRedGreenArea(
      redTiles: List[Point],
      slabIntervals: Map[Slab, List[Interval]]
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
