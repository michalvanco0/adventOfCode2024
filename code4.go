package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func counter(input []string) int {
	count := 0
	directions := [][2]int{
		{0, 1}, {1, 0}, {1, 1}, {1, -1}, {0, -1}, {-1, 0}, {-1, -1}, {-1, 1},
	}
	for r := 0; r < len(input); r++ {
		for c := 0; c < len(input[0]); c++ {
			for dir := 0; dir < len(directions); dir++ {
				if matchesWord(r, c, directions[dir][0], directions[dir][1], input, "XMAS") {
					count++
				}
			}
		}
	}
	return count
}

func matchesWord(row, col, directR, directC int, input []string, goal string) bool {
	for i := 0; i < len(goal); i++ {
		r := row + directR*i
		c := col + directC*i
		if r < 0 || c < 0 || r >= len(input) || c >= len(input[0]) || input[r][c] != goal[i] {
			return false
		}
	}
	return true
}

func counter2(input []string) int {
	count := 0
	for r := 0; r < len(input); r++ {
		for c := 0; c < len(input[0]); c++ {
			if input[r][c] == 'A' {
				if matchesWord(r-1, c-1, 1, 1, input, "MAS") &&
					matchesWord(r+1, c-1, -1, 1, input, "MAS") {
					count++
				} else if matchesWord(r-1, c-1, 1, 1, input, "MAS") &&
					matchesWord(r-1, c+1, 1, -1, input, "MAS") {
					count++
				} else if matchesWord(r+1, c+1, -1, -1, input, "MAS") &&
					matchesWord(r+1, c-1, -1, 1, input, "MAS") {
					count++
				} else if matchesWord(r+1, c+1, -1, -1, input, "MAS") &&
					matchesWord(r-1, c+1, 1, -1, input, "MAS") {
					count++
				}
			}
		}
	}
	return count
}

func main() {
	fileName := "C:\\Users\\User\\adventOfCode2024\\inputs\\input4.txt"
	file, err := os.Open(fileName)
	if err != nil {
		panic(err)
	}
	defer file.Close()
	var input []string
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := strings.TrimSpace(scanner.Text())
		if line != "" {
			input = append(input, line)
		}
	}
	if err := scanner.Err(); err != nil {
		fmt.Printf("Error reading lines: %v\n", err)
		return
	}

	fmt.Println(counter(input))
	fmt.Println(counter2(input))
}
