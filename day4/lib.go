package main

import (
	"bufio"
	"os"
)

// Reads the grid from the file.
func ReadGrid(filename string) ([][]rune, error) {
	file, err := os.Open(filename)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	var grid [][]rune
	scanner := bufio.NewScanner(file)

	for scanner.Scan() {
		line := scanner.Text()
		row := []rune(line)
		grid = append(grid, row)
	}

	if err := scanner.Err(); err != nil {
		return nil, err
	}

	return grid, nil
}

// Counts the number of accessible rolls.
func CountAccessibleRolls(grid [][]rune) int {
	accessibleRolls := 0
	for i, row := range grid {
		for j, char := range row {
			if char == '@' {
				count := countNeighbours(grid, i, j)
				if count < 4 {
					accessibleRolls++
				}
			}
		}
	}

	return accessibleRolls
}

// decleare at package-level to avoid instantiating it each time we call the function
var directions = [8][2]int{
	{-1, -1}, {-1, 0}, {-1, 1},
	{0, -1}, {0, 1},
	{1, -1}, {1, 0}, {1, 1},
}

// Counts the number of paper rolls ('@') in the grid positions adjacent to [row, col].
func countNeighbours(grid [][]rune, row, col int) int {
	count := 0
	for _, direction := range directions {
		newRow := row + direction[0]
		newCol := col + direction[1]

		if (newRow < len(grid) && newRow >= 0) && (newCol < len(grid[0]) && newCol >= 0) {
			if grid[newRow][newCol] == '@' {
				count++
			}
		}
	}

	return count
}
