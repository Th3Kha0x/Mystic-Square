# Mystic Square Puzzle
Mystic Square is a 8-Puzzle / 15-Puzzle solver with [A* algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm)

It is played on a 3-by-3 or 4-by-4 grid with square blocks labeled 1 through N (N being 8 or 15, respectively) and a blank square. Your goal is to rearrange the blocks so that they are in order, using as few moves as possible. You are permitted to slide blocks horizontally or vertically into the blank square. The following is an example sequence of legal moves from an initial board to the goal board. Note that the blank square is textually represented by a 0. 

Step 1. Initial Board (3-by-3)

    0 1 3 
    4 2 5 
    7 8 6 
    
Step 2. Move the `1` block to the left

    1 0 3 
    4 2 5 
    7 8 6 

Step 3. Move the `2` block up

    1 2 3 
    4 0 5 
    7 8 6

Step 4. Move the `5` block to the left

    1 2 3 
    4 5 0 
    7 8 6

Step 5. Move the `6` block up

    1 2 3 
    4 5 6 
    7 8 0 
    
Step 6. Done! (The goal board is reached in 4 moves)

## Structure
- Priority Queues (Min-Heap) are used to keep track of the lowest priority puzzle nodes
- Priority is determined by the heuristic function and the amount of moves required to reach the current node - f(n) + g(n)
- Detection of unsolvable boards by using a pair swapped game board along with the original board and lockstepping.

## Usage
`java PuzzleDriver <input file 1> <input file 2> ...` (Sample input files are provided in the `input` directory)

## Example
Input: `java PuzzleDriver puzzle05.txt`

Output:

    Minimum number of moves = 5
    3
    4 1 3 
    0 2 6 
    7 5 8 

    3
    0 1 3 
    4 2 6 
    7 5 8 

    3
    1 0 3 
    4 2 6 
    7 5 8 

    3
    1 2 3 
    4 0 6 
    7 5 8 

    3
    1 2 3 
    4 5 6 
    7 0 8 

    3
    1 2 3 
    4 5 6 
    7 8 0 
    
<hr/>

Input: `java PuzzleDriver puzzle3x3-unsolvable.txt`

Output: 

    No solution possible
