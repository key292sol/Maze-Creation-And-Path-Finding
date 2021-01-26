package solver;

import java.util.LinkedList;
import java.util.Queue;

import generator.MazeGenerator;
import maze.*;

/*
 * BFS - Breadth First Search
 * Steps:
 * 1. Add start cell to a queue
 * 2. While destination is not reached:
 *      1. Visit a cell from the queue
 *      2. Add neighbors of the cell to the queue
 */
public class BFS extends MazeSolver {
    Queue<Cell> cellQueue;

    public void setGenerator(MazeGenerator gen) {
        super.setGenerator(gen);
        cellQueue = new LinkedList<>();
        cellQueue.add(current);
    }

    public void nextIteration() {
        super.nextIteration();
        
        current = cellQueue.remove();
        if (current == dest) {
            finished = true;
            return;
        }

        for (Cell cell : current.neighbors) {
            cellQueue.add(cell);
            cell.neighbors.remove(current);
            cell.last = current;
        }

        colorCurrentCell(Maze.CURR_CELL_COLOR);
    }
}
