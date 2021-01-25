# Maze Creation and Path Finding

This is a repository to store the implementations of various maze generation and maze solving algorithms.
It can run using IDE or command line terminal.

The main method can be found in main/Main.java class

## Maze Generation Algorithms:
- Randomized Depth First Search
- Randomized Prim's

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
I've implemented a modified version of it which uses cells instead of walls.

### Depth First Search
Instead of using a stack, I have stored the backtracking info in the cells itself.
