#!/usr/bin/env ruby
# frozen_string_literal: true

require_relative 'lib'

devices = File.read('devices.txt').lines

# construct adjacency list
devices_graph = {}
devices.each do |device|
  node, neighbours = device.split(':')
  neighbours = neighbours.split(' ')
  devices_graph[node] = neighbours
end

path_count = dfs("you", devices_graph, Set.new)
printf("[PHASE 1] Total possible paths: %d\n", path_count)
