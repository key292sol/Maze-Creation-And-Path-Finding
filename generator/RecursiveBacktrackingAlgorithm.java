package generator;

import maze.Maze;

// Recursuve Backtracking Algorithm is sometimes also known as Random Depth First Search
public class RecursiveBacktrackingAlgorithm extends MazeGenerator {
    RecursiveBacktrackingAlgorithm(int gridSize) {
        this(gridSize, gridSize);
    }

    public RecursiveBacktrackingAlgorithm(int row, int col) {
        super(row, col);
        current.visited = true;
    }

    public void createWholeMaze() {
        do {
            nextIteration();
        } while (current.last != null);
        
        setGridNotVisited();
    }

    /*
     * 1. Store current cell so to redraw it after the current iteration
     * 2. Get the last cell in the stack
     * 3. If no neighbors are available then remove the last element from cell (Backtrack)
     * 4. Get a random neighbor if there are any available
     * 5. Remove the wall between the current cell and the selected neighbor
     * 6. Add the cell to the stack
     * 
     * If the cellStack is empty then it means we have backtracked till the start point 
     * Set whole grid as not visited to enable path finding 
     */
    public Cell nextIteration() {
        Cell last  = current;
        last.color = Maze.VISITED_COLOR;
        
        Cell selected = getRandomNeighbor(current);
        if (selected == null) {
            if (current.last != null) {
                current = current.last;
                current.color = Maze.CURR_CELL_COLOR;
                return last;
            } else {
                setGridNotVisited();
                return null;
            }
        }

        selected.visited = true;
        removeWall(selected);
        selected.neighbors.remove(current);
        selected.last = current;

        current = selected;
        current.color = Maze.CURR_CELL_COLOR;
        return last;
    }
}
