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
      # take the 1000 edges with the shortest distances
      |> Enum.take(1000)

    num_nodes = length(coords)

    result_phase1 =
      build_components(edges, num_nodes)
      |> component_sizes()
      # take the three largest
      |> Enum.sort(:desc)
      |> Enum.take(3)
      # multiply their sizes
      |> Enum.product()

    IO.puts("[PHASE 1] Result: #{result_phase1}")

    result_phase2 = 0
    IO.puts("[PHASE 2] Result: #{result_phase2}")
  end
end
