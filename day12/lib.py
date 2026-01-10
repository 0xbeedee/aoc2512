from __future__ import annotations

import math

from grid import Grid


def shapes_fit(
    grid: Grid,
    shape_transforms: list[list[set[tuple[int, int]]]],
    shape_counts: list[int],
) -> bool:
    """Checks if a number of shapes can fit within the specified grid."""
    # checking the are is apparently enough for the real input (part 1)
    cells_needed = sum(
        len(shape_transforms[i][0]) * count for i, count in enumerate(shape_counts)
    )
    if cells_needed <= grid.width * grid.height:
        return True
    return False

    # useless backtracking...
    #
    # all_placed = all([count == 0 for count in shape_counts])
    # if all_placed:
    #     # all shapes have been placed, according to their counts
    #     return True

    # position = grid.find_first_empty_cell()
    # print(f"Trying position {position}, counts={shape_counts}")  # debug
    # if position is None:
    #     # grid is filled, and all the shapes have been placed
    #     return all_placed

    # for shape_type in range(len(shape_counts)):
    #     if shape_counts[shape_type] == 0:
    #         # all shapes already placed, skip iteration
    #         continue

    #     for transformation in shape_transforms[shape_type]:
    #         if grid.can_place(transformation, position):
    #             # place
    #             shape_counts[shape_type] -= 1
    #             grid.place(transformation, position)
    #             # recurse
    #             if shapes_fit(grid, shape_transforms, shape_counts):
    #                 return True
    #             # undo placement, if no recursion
    #             grid.unplace(transformation, position)
    #             shape_counts[shape_type] += 1  # undo decrement

    # grid.occupied.add(position)  # try another empty cell at the next iteration
    # result = shapes_fit(grid, shape_transforms, shape_counts)
    # grid.occupied.remove(position)  # restore the grid
    # return result


def generate_transformations(shape_str: str) -> list[set[tuple[int, int]]]:
    """Generates all the possible rotations and flips of a given shape, represented as a string."""
    rotations = [90, 180, 270]  # 360 brings us back to the starting point

    init_coords = _parse_shape_string(shape_str)
    transformations = [init_coords]

    # we can generate all the flips by flipping the original, then rotating it!
    og_flipped = _normalise(_flip_horizontal(transformations[0]))
    if og_flipped not in transformations:
        transformations.append(og_flipped)

    # rotate and flip
    for rot in rotations:
        # always rotate from the initial state
        rotated = _normalise(_rotate(transformations[0], rot))
        flipped = _normalise(_rotate(og_flipped, rot))

        if rotated not in transformations:  # only include unique shapes
            transformations.append(rotated)
        if flipped not in transformations:
            transformations.append(flipped)

    return transformations


def _parse_shape_string(shape_str: str) -> set[tuple[int, int]]:
    """Returns the coordinate representation corresponding to the shape string."""
    # get the coordinates of the '#' chars
    coord_set = set()
    lines = shape_str.split("\n")[1:]  # skip the first line ("n:")
    for i, line in enumerate(lines):
        for j in range(len(line)):
            if line[j] == "#":
                coord_set.add((i, j))
    return coord_set


def _rotate(shape: set[tuple[int, int]], amount: int) -> set[tuple[int, int]]:
    """Rotates a shape by a specified amount (in degrees)."""
    cosine = int(math.cos(math.radians(amount)))
    sine = int(math.sin(math.radians(amount)))
    return {(x * cosine + y * sine, -x * sine + y * cosine) for x, y in shape}


def _flip_horizontal(shape: set[tuple[int, int]]) -> set[tuple[int, int]]:
    """Flips a shape horizontally."""
    return {(-x, y) for x, y in shape}


def _normalise(shape: set[tuple[int, int]]) -> set[tuple[int, int]]:
    """Normalises a shape by making sure that it's top-left corner is at (0, 0)."""
    min_x = min(x for x, y in shape)
    min_y = min(y for x, y in shape)
    return {(x - min_x, y - min_y) for x, y in shape}
