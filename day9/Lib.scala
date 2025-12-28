object Lib:

  // Computes the largest area, given the 2D coordinates of the red tiles.
  def largest_area(red_tiles: List[(Long, Long)]): Long =
    red_tiles
      .combinations(2)
      .map { tiles =>
        compute_area(tiles(0), tiles(1))
      }
      .max

  def graham_scan(red_tiles: List[(Long, Long)]): List[(Long, Long)] =
    val anchor_point = red_tiles.minBy(p => (p._2, p._1))
    val sorted_tiles = red_tiles
      .sortBy { tile =>
        val (ax, ay) = anchor_point
        val (tx, ty) = tile
        math.atan2(ty - ay, tx - ax)
      }

    sorted_tiles.foldLeft(List.empty[(Long, Long)]) { (stack, point) =>
      processPoint(stack, point)
    }

  def processPoint(
      stack: List[(Long, Long)],
      point: (Long, Long)
  ): List[(Long, Long)] =
    if (stack.length < 2) point :: stack
    else if (cross_product(stack(1), stack(0), point) > 0)
      // the three points make a left turn
      point :: stack
    else
      // the three points make a right turn
      processPoint(stack.tail, point)

  // Computes the area between two tiles
  def compute_area(tile1: (Long, Long), tile2: (Long, Long)): Long =
    val (x1, y1) = tile1
    val (x2, y2) = tile2
    // +1 to include the borders
    ((x1 - x2).abs + 1) * ((y1 - y2).abs + 1)

  // Computes the 2D cross-product between three tiles.
  def cross_product(
      tile1: (Long, Long),
      tile2: (Long, Long),
      tile3: (Long, Long)
  ): Long =
    val (x1, y1) = tile1
    val (x2, y2) = tile2
    val (x3, y3) = tile3
    (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)
