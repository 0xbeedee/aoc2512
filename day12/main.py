from __future__ import annotations

from grid import Grid
from lib import generate_transformations, shapes_fit


def parse_input(
    shapes_regions: list[str],
) -> tuple[list[list[set[tuple[int, int]]]], list[tuple[tuple[int, int], list[int]]]]:
    shapes_str, regions_str = shapes_regions[:-1], shapes_regions[-1]

    shape_transforms = [generate_transformations(shape) for shape in shapes_str]

    regions = []  # [((reg_x, reg_y), [#required shapes]), ...]
    for region in regions_str.split("\n"):
        if not region:
            continue
        spec, shapes_list = region.split(":", maxsplit=1)
        spec = spec.split("x")
        spec_tuple = (int(spec[0]), int(spec[1]))
        regions.append((spec_tuple, [int(n) for n in shapes_list.lstrip().split(" ")]))

    return shape_transforms, regions


if __name__ == "__main__":
    with open("presents.txt", "r") as pf:
        input = pf.read().split("\n\n")  # split on empty lines

    shape_transforms, regions = parse_input(input)

    num_fitting_regions = 0
    for (width, height), required_shapes in regions:
        region_grid = Grid(height=height, width=width)
        result = shapes_fit(region_grid, shape_transforms, required_shapes.copy())
        if result:
            num_fitting_regions += 1

    print(
        f"[PHASE 1] Number of regions which can fit the shapes: {num_fitting_regions}"
    )
