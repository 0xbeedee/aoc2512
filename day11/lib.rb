# frozen_string_literal: true

def dfs(cur_node, graph, visited)
  # found a path to out
  return 1 if cur_node == 'out'

  visited.add(cur_node)

  path_count = 0
  neighbours = graph[cur_node] || []
  neighbours.each do |neighbour|
    next if visited.include?(neighbour) # skip visited nodes

    path_count += dfs(neighbour, graph, visited)
  end

  # unmark to not pollute other paths
  visited.delete(cur_node)

  path_count
end
