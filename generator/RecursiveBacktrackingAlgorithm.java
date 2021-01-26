package generator;

import maze.*;

/*
 * Recursive Backtracking Algorithm is sometimes also known as Random Depth First Search
 * 
 * Steps:
 * 1. Store current cell so to redraw it after the current iteration
 * 2. Get the last cell in the stack
 * 3. If no neighbors are available then remove the last element from cell (Backtrack)
 * 4. Get a random neighbor if there are any available
 * 5. Remove the wall between the current cell and the selected neighbor
 * 6. Add the cell to the stack
 * 
 * If the cellStack is empty then it means we have backtracked till the start point 
 * Set whole grid as not visited to enable path finding
 * 
 * Changes:
 * Instead of using a stack, i have stored the backtracking info in the cells
 */
public class RecursiveBacktrackingAlgorithm extends MazeGenerator {
    
    public RecursiveBacktrackingAlgorithm(int gridSize) {
        this(gridSize, gridSize);
    }

    public RecursiveBacktrackingAlgorithm(int row, int col) {
        super(row, col);
        current.visited = true;
    }

    @Override
    public void nextIteration() {
        super.nextIteration();
        
        Cell selected = getRandomNeighbor(current);
        // (selected == null) will be true when the cell has no unvisited neighbors
        if (selected == null) {
            // If we have not reached to the start node then backtrack
            // Else the maze generation is completed
            if (current.last != null) {
                current = current.last;
                colorCurrentCell(Maze.CURR_CELL_COLOR);
            } else {
                setGridNotVisited();
            }
            return;
        }

        // Cell has unvisited neighbors
        // Remove the walls and add go to the neighbor
        selected.visited = true;
        removeWall(selected);
        selected.neighbors.remove(current);
        selected.last = current;

        current = selected;
        colorCurrentCell(Maze.CURR_CELL_COLOR);
    }
}
