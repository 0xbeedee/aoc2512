from __future__ import annotations


class Grid:
    """Represents a 2D grid for packing shapes."""

    def __init__(self, width: int, height: int):
        self.width = width
        self.height = height
        self.occupied: set[tuple[int, int]] = set()

    def place(self, shape: set[tuple[int, int]], position: tuple[int, int]) -> None:
        """Places a shape at the given position on the grid (modifies grid in-place)."""
        pos_x, pos_y = position
        translated = {(x + pos_x, y + pos_y) for x, y in shape}
        self.occupied |= translated

    def unplace(self, shape: set[tuple[int, int]], position: tuple[int, int]) -> None:
        """Removes a shape from the given position on the grid (modifies grid in-place)."""
        pos_x, pos_y = position
        translated = {(x + pos_x, y + pos_y) for x, y in shape}
        self.occupied -= translated

    def can_place(self, shape: set[tuple[int, int]], position: tuple[int, int]) -> bool:
        """Checks if a shape can be placed at the given position without overlap or exceeding bounds."""
        pos_x, pos_y = position
        # translate to target position
        translated = {(x + pos_x, y + pos_y) for x, y in shape}

        # all coordinates must be within [0, width) and [0, height)
        for x, y in translated:
            if x < 0 or x >= self.height or y < 0 or y >= self.width:
                return False

        # the translated shape must not intersect with occupied cells
        if translated & self.occupied:
            return False
        return True

    def can_place_somewhere(self, shape: set[tuple[int, int]]) -> bool:
        """Checks if we can place a certain shape somewhere in the grid."""
        for row in range(self.height):
            for col in range(self.width):
                if self.can_place(shape, (row, col)):
                    return True
        return False

    def find_first_empty_cell(self) -> tuple[int, int] | None:
        """Returns the first empty cell in the grid, scanning from top left to bottom right."""
        for row in range(self.height):
            for col in range(self.width):
                if (row, col) not in self.occupied:
                    return (row, col)
        return None  # grid is completely full
