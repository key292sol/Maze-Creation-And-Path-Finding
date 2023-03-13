# Maze Creation and Path Finding

This is a repository to store the implementations of various maze generation and maze solving algorithms.

The main method can be found in main/Main.java class

## Maze Generation Algorithms:
- Randomized Depth First Search (Backtracker)
- Randomized Prim's
- Hunt and Kill
- Binary Tree Maze generation
- Sidewinder

## Path Finding Algorithms:
- Depth First Search (DFS)
- Breadth First Search (BFS)
- Optimised DFS
- A* (A-star) path finding

## Useful Links
- [Wikipedia - Maze generation algorithms](https://en.m.wikipedia.org/wiki/Maze_generation_algorithm)
- [Wikipedia - A* search algorithm](https://en.m.wikipedia.org/wiki/A*_search_algorithm)

## Notes

### Random Prim's:
Implemented a modified version of it which uses cells instead of walls.

### Binary Tree
It is implemented so that it carves a passage to the left or above the current cell.

### Depth First Search
Instead of using a stack, the backtracking data is stored in the cells themself.
