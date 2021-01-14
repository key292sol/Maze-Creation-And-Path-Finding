package generator;

import java.util.ArrayList;
import maze.*;

public class RandomPrims extends MazeGenerator {
	ArrayList<Cell> cellsList;
	RandomPrims(int gridSize) {
        this(gridSize, gridSize);
    }

    public RandomPrims(int row, int col) {
        super(row, col);
        current.visited = true;
        cellsList = new ArrayList<>();
        cellsList.add(current);
        // addNeighbors(current);
    }

    public void createWholeMaze() {
        do {
            nextIteration();
        } while (cellsList.size() > 0);
        
        setGridNotVisited();
    }

    public Cell nextIteration() {
        if (cellsList.size() == 0) {
            setGridNotVisited();
            return null;
        }

    	Cell last = current;
        last.color = Maze.VISITED_COLOR;
        
        current = cellsList.get(random.nextInt(cellsList.size()));
        current.color = Maze.CURR_CELL_COLOR;
        Cell selected = getRandomNeighbor(current);

        if (selected == null) {
            cellsList.remove(current);
        } else if (!selected.visited) {
            selected.visited = true;
            removeWall(selected);
            cellsList.add(selected);
            cellsList.add(current);
        }

        /* current = cellsList.remove(random.nextInt(cellsList.size()));

        current.color = CURR_CELL_COLOR;
        Cell selected = getRandomNeighbor(current);

    	if (selected != null && !selected.visited) {
            selected.visited = true;
            cellsList.add(selected);
            removeWall(selected);
            selected.neighbors.remove(current);
    		addNeighbors(selected);
        } */
    	return last;
    }
}