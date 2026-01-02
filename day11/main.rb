#!/usr/bin/env ruby
# frozen_string_literal: true

require_relative 'lib'

# construct adjacency list
devices_graph = File.readlines('devices.txt', chomp: true).each_with_object({}) do |line, graph|
  node, neighbours = line.split(':', 2)
  graph[node] = neighbours.split
end

# false => graph is a DAG
# printf("Is graph cyclic? %s\n", graph_has_cycles?(devices_graph) ? "true" : "false")

path_count = count_paths(devices_graph, 'you', 'out')
printf("[PHASE 1] Total possible paths: %d\n", path_count)

path_count = count_paths(devices_graph, 'svr', 'out', ['dac', 'fft'])
printf("[PHASE 2] Total possible paths, with DAC and FFT: %d\n", path_count)
