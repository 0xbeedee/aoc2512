package main

import "fmt"

func main() {
	grid, err := ReadGrid("rolls.txt")
	if err != nil {
		fmt.Println("Error reading file: ", err)
		return
	}
	// printGrid(grid)
	rollCount, _ := CountAccessibleRolls(grid)
	fmt.Println("[PHASE 1] Number of accessible rolls:", rollCount)

	removableCount := CountRemovableRolls(grid)
	fmt.Println("[PHASE 1] Number of removable rolls:", removableCount)
}

// Prints the grid as a 2D matrix.
func printGrid(grid [][]rune) {
	for _, row := range grid {
		for _, char := range row {
			fmt.Printf("%c", char)
		}
		fmt.Println()
	}
	fmt.Println()
}
