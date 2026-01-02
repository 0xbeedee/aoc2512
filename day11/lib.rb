# frozen_string_literal: true

##
# Count paths in a DAG from start to target that visit all required nodes
#
# This works because the graph is a DAG (no cycles), so we don't need a visited set -
# following edges naturally prevents revisiting nodes (which would create a cycle).
#
# Uses dynamic programming with bitmask memoization to efficiently count the
# exponentially many paths without enumerating them individually.
def count_paths(graph, start, target, required_nodes = [])
  memo = {}

  # calculate initial bitmask
  start_mask = 0
  required_nodes.each_with_index do |req, i|
    start_mask |= (1 << i) if start == req
  end

  # full mask = all required nodes visited (0b11 for 2 nodes)
  full_mask = (1 << required_nodes.size) - 1

  # get all paths grouped by which required nodes they visited
  all_paths = dfs_with_requirements(start, start_mask, graph, target, required_nodes, memo)

  # paths that visited all required nodes
  all_paths[full_mask] || 0
end

##
# Recursively count paths from a node to target, tracking required nodes
#
# Returns a hash mapping final_mask -> count, where:
#   - final_mask: bitmask of which required nodes were visited
#   - count: number of paths that end with that mask
#
# Example: {0b01 => 100, 0b11 => 50} means:
#   - 100 paths visited only the first required node
#   - 50 paths visited both required nodes
def dfs_with_requirements(node, current_mask, graph, target, required_nodes, memo)
  # reached target, return one path with current mask
  return { current_mask => 1 } if node == target

  # check memoisation cache
  key = [node, current_mask]
  return memo[key] if memo.key?(key)

  # aggregate results from all neighbors
  result = Hash.new(0)
  (graph[node] || []).each do |neighbour|
    # update mask if this neighbor is a required node
    new_mask = current_mask
    required_nodes.each_with_index do |req, i|
      new_mask |= (1 << i) if neighbour == req
    end

    sub_result = dfs_with_requirements(neighbour, new_mask, graph, target, required_nodes, memo)
    sub_result.each { |final_mask, count| result[final_mask] += count }
  end

  # memoise
  memo[key] = result
  result
end

def cycle?(graph, node, visiting = Set.new, visited = Set.new)
  return false if visited.include?(node)  # already fully explored
  return true if visiting.include?(node)  # found a back edge = cycle!

  visiting.add(node)

  neighbours = graph[node] || []
  neighbours.each do |neighbour|
    return true if cycle?(graph, neighbour, visiting, visited)
  end

  visiting.delete(node)
  visited.add(node)
  false
end

def graph_has_cycles?(graph)
  visited = Set.new
  graph.each_key do |node|
    next if visited.include?(node)
    return true if cycle?(graph, node, Set.new, visited)
  end
  false
end
