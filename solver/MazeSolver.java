package solver;

import generator.*;
import maze.*;

public abstract class MazeSolver extends Maze {
    Cell start, dest;
    boolean finished = false;

    public void setGenerator(MazeGenerator gen) {
        this.blockSize = gen.blockSize;
        this.maze = gen.maze;

        current = start = getCellAt(0, 0);
        dest = getCellAt(maze.length-1, maze[0].length-1);
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