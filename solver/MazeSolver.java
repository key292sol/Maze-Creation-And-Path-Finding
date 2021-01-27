package solver;

import generator.*;
import maze.*;
import java.awt.Color;

public abstract class MazeSolver extends Maze {
    public Cell start, dest;

    // @param   gen The generator which created the maze to solve
    public void setGenerator(MazeGenerator gen) {
        this.maze = gen.maze;

        // start at top left and end at bottom right
        current = start = getCellAt(0, 0);
        dest = getCellAt(maze.length-1, maze[0].length-1);
        start.color = Maze.START_COLOR;
        dest.color = Maze.DESTINATION_COLOR;
    }

    // Solve the maze without visualization
    @Override
    public void completeAllIterations() {
        super.completeAllIterations();
        while (current.last != start) {
            lastPathCell();
        }
    }

    // Backtrack one by one till start cell after finding the destination
    public void lastPathCell() {
        if (current.last != start) {
            current = current.last;
            current.color = Maze.PATH_COLOR;
        }
    }

    public void colorCurrentCell(Color color) {
        if (current != start && current != dest) {
            current.color = color;
            changedCells.add(current);
        }
    }
}