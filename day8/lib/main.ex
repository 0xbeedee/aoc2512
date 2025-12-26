defmodule Day8.Main do
  @moduledoc """
  Main entry point for Day 8 solution
  """
  import Day8

  def main do
    coords =
      File.read!("jboxes.txt")
      |> String.split("\n", trim: true)
      |> Enum.map(fn line ->
        line
        |> String.split(",")
        |> Enum.map(&String.to_integer/1)
        |> List.to_tuple()
      end)

    edges =
      coords
      |> all_distances()
      |> Enum.sort()

    num_nodes = length(coords)

    ###################### PHASE 1 ###########################

    # take the 1000 edges with the shortest distances, for phase 1
    first_1000 = edges |> Enum.take(1000)

    result_phase1 =
      build_components(first_1000, num_nodes)
      |> component_sizes()
      # take the three largest
      |> Enum.sort(:desc)
      |> Enum.take(3)
      # multiply their sizes
      |> Enum.product()

    IO.puts("[PHASE 1] Product of the sizes of the three largest components: #{result_phase1}")

    ###################### PHASE 2 ###########################

    {i, j} = get_final_edge(edges, num_nodes)
    {x1, _, _} = Enum.at(coords, i)
    {x2, _, _} = Enum.at(coords, j)
    result_phase2 = x1 * x2

    IO.puts(
      "[PHASE 2] Product of the X coordinates of the last two junction boxes: #{result_phase2}"
    )
  end
end
