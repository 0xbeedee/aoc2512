defmodule Day8 do
  @moduledoc """
  Documentation for `Day8`.
  """

  @doc """
  Calculates the Euclidean distance between two 3D points.
  """
  def distance({x1, y1, z1}, {x2, y2, z2}) do
    :math.sqrt((x1 - x2) ** 2 + (y1 - y2) ** 2 + (z1 - z2) ** 2)
  end

  @doc """
  Calculates all pairwise distances between the coordinates.
  """
  def all_distances(coords) do
    for {point1, i} <- Enum.with_index(coords),
        {point2, j} <- Enum.with_index(coords),
        # distances are symmetric, can save computation by only computing the upper triangle
        i < j do
      # keep track of the nodes as well
      {distance(point1, point2), i, j}
    end
  end

  @doc """
  Constructs the connected components, given the 1000 pairs of jboxes which are closest together.
  """
  def build_components(edges, num_nodes) do
    # init each node to its own connected component
    init = for i <- 0..(num_nodes - 1), do: MapSet.new([i])

    Enum.reduce(edges, init, fn {_dist, i, j}, components ->
      set_with_i = Enum.find(components, fn set -> MapSet.member?(set, i) end)
      set_with_j = Enum.find(components, fn set -> MapSet.member?(set, j) end)

      # different components => merge
      if(set_with_i != set_with_j) do
        merged = MapSet.union(set_with_i, set_with_j)
        remaining = Enum.filter(components, fn set -> set != set_with_i and set != set_with_j end)
        [merged | remaining]
      else
        components
      end
    end)
  end
end
