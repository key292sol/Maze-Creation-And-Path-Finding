package generator;

import java.util.ArrayList;
import maze.*;

/*
 * Algorithm steps:
 * 1. Start with a grid full of walls
 * 2. Pick a cell, mark it as part of maze.
 *    Add its wall to the wall list.
 * 3. While there are walls in the list
 *      1. Pick a random wall. If only one of the 2 cells is visited:
 *          1. Make the wall a passage
 *          2. Add neighbouring walls of cell to the list
 *      2. Remove the wall from list
 * 
 * Chenges in algorithm:
 * 1. Instead of adding walls, i added the cells to the list
 * 2. Instead of picking a random wall, i picked a random cell and its random neighbor
 */
public class RandomPrims extends MazeGenerator {
    ArrayList<Cell> cellsList;
    
	public RandomPrims(int gridSize) {
        this(gridSize, gridSize);
    }

    public RandomPrims(int row, int col) {
        super(row, col);
        current.visited = true;
        cellsList = new ArrayList<>();
        cellsList.add(current);
    }

    @Override
    public Cell nextIteration() {
        // If no cells left to evaluate
        if (cellsList.size() == 0) {
            setGridNotVisited();
            return null;
        }

    	Cell last = current;
        last.color = Maze.VISITED_COLOR;
        
        // Get a random cell from list and a random neighbor
        current = cellsList.get(random.nextInt(cellsList.size()));
        current.color = Maze.CURR_CELL_COLOR;
        Cell selected = getRandomNeighbor(current);

        // If cell has no unvisited neighbors left then return
        // Else remove the walls and dd the selected cell to the list
        if (selected == null) {
            cellsList.remove(current);
        } else {
            selected.visited = true;
            removeWall(selected);
            cellsList.add(selected);
        }
        
    	return last;
    }
}