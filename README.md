# 15 Puzzle Solver

![15 Puzzle Example](https://via.placeholder.com/150?text=15+Puzzle)

## Overview

This project implements an intelligent solver for the classic 15-puzzle problem using various search algorithms. The 15-puzzle consists of a 4×4 grid with 15 numbered tiles and one empty space. The goal is to rearrange the tiles from a given initial configuration to a specified final configuration by sliding tiles into the empty space.

## Features

- Multiple search algorithm implementations
- Customizable heuristics
- Performance statistics tracking
- Input validation with solvability checks
- Clean object-oriented design

## Search Algorithms

The following search algorithms are implemented:

| Algorithm | Description | Completeness | Optimality | Space Complexity | Time Complexity |
|-----------|-------------|--------------|------------|-----------------|-----------------|
| BFS | Breadth-First Search | Yes | Yes | O(b^d) | O(b^d) |
| DFS | Depth-First Search | No | No | O(bm) | O(b^m) |
| IDFS | Iterative Deepening DFS | Yes | Yes | O(bd) | O(b^d) |
| A* | A* Search with heuristics | Yes | Yes | O(b^d) | O(b^d) |
| Greedy | Greedy Best-First Search | No | No | O(b^d) | O(b^d) |

> Where b is the branching factor, d is the depth of the solution, and m is the maximum depth.

## Heuristics

Two heuristic functions are provided for A* and Greedy search:

1. **Off-site parts count (Hamming distance)**: Counts the number of tiles that are not in their goal position.
2. **Manhattan distance**: Sum of the horizontal and vertical distances of each tile from its goal position.

## Installation

### Prerequisites
- Java JDK 8 or higher

### Compilation
```bash
javac Puzzle15.java GameBoard.java TypesOfSearch.java Heuristic.java BoardComparator.java
```

### Running the Application
```bash
java Puzzle15
```

## Usage

### Input Format

When prompted, enter the initial and final board configurations row by row. Each board configuration should be provided as a 4x4 grid of numbers (0-15), where 0 represents the empty space.

#### Example Input:
```
Initial Board:
1 2 3 4
5 6 8 0
9 10 7 12
13 14 11 15

Final Board:
1 2 3 4
5 6 7 8
9 10 11 12
13 14 15 0
```

### Selecting a Search Strategy

After entering the board configurations, you will be prompted to choose a search strategy:

1. Breadth-First Search (BFS)
2. Depth-First Search (DFS)
3. Iterative Deepening Depth-First Search (IDFS)
4. A* Search
5. Greedy Search

For A* and Greedy search, you will also need to select a heuristic function:

1. Off-site parts count (Hamming distance)
2. Manhattan distance

### Interpreting the Results

The program will display:
- The solution path from initial to goal state
- Execution time
- Depth of the solution
- Number of states generated
- Number of states visited

## Implementation Details

### Project Structure

- `Puzzle15.java`: Main class with user interface
- `GameBoard.java`: Board representation and operations
- `TypesOfSearch.java`: Search algorithm implementations
- `Heuristic.java`: Heuristic functions
- `BoardComparator.java`: Comparator for priority queue-based searches

### Algorithm Pseudocode

#### BFS
```
function BFS(initial_state, goal_state):
    create queue Q
    add initial_state to Q
    create set visited
    add initial_state to visited
    while Q is not empty:
        current = Q.dequeue()
        if current equals goal_state:
            return path from initial_state to goal_state
        for each action in getActions(current):
            child = makeAction(current, action)
            if child not in visited:
                add child to visited
                add child to Q
    return failure
```

## Performance Considerations

- BFS guarantees the optimal solution but requires more memory
- DFS uses less memory but may not find the optimal solution
- A* with Manhattan distance typically provides the best balance of optimality and efficiency
- For complex puzzles, memory usage can be significant

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Based on the classic 15-puzzle problem
- Inspired by academic research in heuristic search algorithms