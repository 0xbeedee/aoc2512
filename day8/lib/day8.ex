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
    # {parent: node} map for union-find
    init_parent = Map.new(for i <- 0..(num_nodes - 1), do: {i, i})
    # {parent: rank} map for union-by-rank
    init_rank = Map.new(for i <- 0..(num_nodes - 1), do: {i, 0})

    {parent, _rank} =
      Enum.reduce(edges, {init_parent, init_rank}, fn {_dist, i, j}, {parent, rank} ->
        union(parent, rank, i, j)
      end)

    parent
  end

  @doc """
  Computes the sizes of the connected components.
  """
  def component_sizes(parent) do
    parent
    |> Enum.map(fn {node, _} -> find(parent, node) end)
    |> Enum.frequencies()
    |> Map.values()
  end

  # Implements the find() part for a union-find forest.
  defp find(parent, node) do
    if parent[node] == node do
      # found the root
      node
    else
      find(parent, parent[node])
    end
  end

  # Implements the union() part for a union-find forest.
  defp union(parent, rank, i, j) do
    root_i = find(parent, i)
    root_j = find(parent, j)

    cond do
      root_i == root_j ->
        # already connected
        {parent, rank}

      rank[root_i] < rank[root_j] ->
        # attach i under j
        {Map.put(parent, root_i, root_j), rank}

      rank[root_i] > rank[root_j] ->
        # attach j under i
        {Map.put(parent, root_j, root_i), rank}

      true ->
        # equal ranks, attach ad lib.
        new_parent = Map.put(parent, root_i, root_j)
        new_rank = Map.put(rank, root_j, rank[root_j] + 1)
        {new_parent, new_rank}
    end
  end
end
