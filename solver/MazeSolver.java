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

        current = start = getRandomCell(row, col);
        dest = getRandomCell(row, col);
        start.color = Maze.START_COLOR;
        dest.color = Maze.DESTINATION_COLOR;
    }

    public abstract void pathFound();
}