package solver;

import java.util.LinkedList;
import java.util.Queue;

import generator.MazeGenerator;
import maze.*;

public class BFS extends MazeSolver {
    Queue<Cell> cellQueue;

    public void setGenerator(MazeGenerator gen) {
        super.setGenerator(gen);
        cellQueue = new LinkedList<>();
        cellQueue.add(current);
    }

    public Cell nextIteration() {
        Cell last  = current;
        if (last != start && last != dest)
            last.color = VISITED_COLOR;

        if (current == dest) {
            finished = true;
            return null;
        }

        for (Cell cell : current.neighbors) {
            cellQueue.add(cell);
            cell.neighbors.remove(current);
            cell.last = current;
        }
        current.neighbors.clear();
        current = cellQueue.remove();
        if (current != start && current != dest)
            current.color = Maze.CURR_CELL_COLOR;

        return last;
    }

    public void pathFound() {
        current = current.last;                             // Popping the destintion node
        while (current.last != null) {
            current.color = Maze.PATH_COLOR; // Popping each node in path and changing its color
            current = current.last;
        }
    }
}
