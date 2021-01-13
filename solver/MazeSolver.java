package solver;

import generator.*;
import maze.Maze;

public abstract class MazeSolver extends Maze {
    Cell start, dest;
    boolean finished = false;

    MazeSolver(int row, int col) {
        MazeGenerator gen = new RecursiveBacktrackingAlgorithm(row, col);
        gen.createWholeMaze();
        this.blockSize = gen.blockSize;
        this.maze = gen.maze;

        current = start = getCellAt(0, 0);
        dest = getCellAt(row-1, col-1);
        start.color = Maze.START_COLOR;
        dest.color = Maze.DESTINATION_COLOR;
    }

    public abstract void pathFound();

    public void lastPathCell() {
        if (current.last != start) {
            current = current.last;
            current.color = Maze.PATH_COLOR;
        }
    }
}