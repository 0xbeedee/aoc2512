package main

import "fmt"

func main() {
	grid, err := ReadGrid("rolls.txt")
	if err != nil {
		fmt.Println("Error reading file: ", err)
		return
	}
	// printGrid(grid)
	rollCount := CountAccessibleRolls(grid)
	fmt.Println("[PHASE 1] Number of accessible rolls:", rollCount)
}

func printGrid(grid [][]rune) {
	for _, row := range grid {
		for _, char := range row {
			fmt.Printf("%c", char)
		}
		fmt.Println()
	}
	fmt.Println()
}
