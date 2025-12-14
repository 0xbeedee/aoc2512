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
func CountAccessibleRolls(grid [][]rune) (int, [][]int) {
	accessibleRolls := 0
	var toRemove [][]int
	for i, row := range grid {
		for j, char := range row {
			if char == '@' {
				count := countNeighbours(grid, i, j)
				if count < 4 {
					accessibleRolls++
					toRemove = append(toRemove, []int{i, j})
				}
			}
		}
	}

	return accessibleRolls, toRemove
}

// Counts the amount of rolls which can be removed.
func CountRemovableRolls(grid [][]rune) int {
	removableRolls := 0
	for {
		numAccesible, toRemove := CountAccessibleRolls(grid)
		if numAccesible == 0 {
			break
		}
		// remove all the accessible rolls in one sweep!
		removeAllAccessible(grid, toRemove)
		removableRolls += numAccesible
	}
	return removableRolls
}

func removeAllAccessible(grid [][]rune, toRemove [][]int) {
	for _, pos := range toRemove {
		grid[pos[0]][pos[1]] = '.'
	}
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
