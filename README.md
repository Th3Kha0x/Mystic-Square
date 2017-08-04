# Mystic Square
8-Puzzle / 15-Puzzle solver with [A* algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm)

## Structure
- Priority Queues (Min-Heap) are used to keep track of the lowest priority puzzle nodes
- Priority is determined by the heuristic function and the amount of moves required to reach the current node - f(n) + g(n)
- Detection of unsolvable boards by using a pair swapped game board along with the original board and lockstepping.

## Usage
`java PuzzleDriver <input file 1> <input file 2> ...` (Sample input files are provided in the `input` directory)
