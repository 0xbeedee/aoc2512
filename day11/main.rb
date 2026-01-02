#!/usr/bin/env ruby
# frozen_string_literal: true

require_relative 'lib'

# construct adjacency list
devices_graph = File.readlines('devices.txt', chomp: true).each_with_object({}) do |line, graph|
  node, neighbours = line.split(":", 2)
  graph[node] = neighbours.split
end

path_count = dfs("you", devices_graph, Set.new)
printf("[PHASE 1] Total possible paths: %d\n", path_count)
