package maze;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public abstract class Maze {
    public static final Color VISITED_COLOR      = Color.LIGHT_GRAY;
    public static final Color NOT_VISITED_COLOR  = Color.WHITE;
    public static final Color CURR_CELL_COLOR    = Color.YELLOW;
    public static final Color PATH_COLOR         = Color.GREEN;
    public static final Color START_COLOR        = Color.RED;
    public static final Color DESTINATION_COLOR  = Color.BLUE;
    
    public static final int frameWidth = 760, frameHeight = 760;

    public Cell[][] maze;
    public Cell current, last;
    public boolean finished = false;
    public ArrayList<Cell> changedCells = new ArrayList<>();

    public abstract void colorCurrentCell(Color color);

    // For performing a new iteration of solver or generator
    public void nextIteration() {
        changedCells.clear();
        colorCurrentCell(Maze.VISITED_COLOR);
    }

    // If you don't want to visualize
    public void completeAllIterations() {
        while (!finished) {
            nextIteration();
        }
    }

    public Cell getCellAt(int r, int c) {
        if (r < 0 || c < 0 || r >= maze.length || c >= maze[0].length)
            return null;
        return maze[r][c];
    }

    public Cell getRandomCell(int rows, int cols) {
        Random random = new Random();
        return getCellAt(random.nextInt(rows), random.nextInt(cols));
    }
}
