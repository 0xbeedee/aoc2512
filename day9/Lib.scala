object Lib:

  // Computes the largest area, given the 2D coordinates of the red tiles.
  def largest_area(red_tiles: List[(Long, Long)]): Long =
    red_tiles
      .combinations(2)
      .map { tiles =>
        compute_area(tiles(0), tiles(1))
      }
      .max

  // Computes the area between two tiles
  def compute_area(tile1: (Long, Long), tile2: (Long, Long)): Long =
    val (x1, y1) = tile1
    val (x2, y2) = tile2
    // +1 to include the borders
    ((x1 - x2).abs + 1) * ((y1 - y2).abs + 1)
