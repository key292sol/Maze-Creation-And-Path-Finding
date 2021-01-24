package maze;

import java.util.Random;
import java.awt.Graphics;
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
    public Cell current;
    public int blockSize;

    // For performing a new iteration of solver or generator
    // Must return null on completion
    public abstract Cell nextIteration();

    // If you don't want to visualize
    public void completeAllIterations() {
        while (nextIteration() != null) {}
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

    /*
     * @param   g       The Graphics object on which you need to draw cell
     * @param   cell    The cell needed to be drawn
     */
    public void drawCell(Graphics g, Cell cell) {
        // Draw the rectangle/square shape of the cell
        g.setColor(cell.color);
        g.fillRect(getDrawXPos(cell.col) + 1, getDrawYPos(cell.row) + 1, blockSize, blockSize);

        // Draw walls
        g.setColor(Color.BLACK);
        int[][] walls = {                                                               // x1, y1, x2, y2 positions
                { (cell.col),     (cell.row),     (cell.col + 1), (cell.row)     },     // top wall
                { (cell.col + 1), (cell.row),     (cell.col + 1), (cell.row + 1) },     // right wall
                { (cell.col),     (cell.row + 1), (cell.col + 1), (cell.row + 1) },     // bottom wall
                { (cell.col),     (cell.row),     (cell.col),     (cell.row + 1) }      // left wall
        };

        // If there exists a wall there, then draw a line
        // representing the wall
        for (int i = 0; i < walls.length; i++) {
            if (cell.walls[i]) {
                g.drawLine(getDrawXPos(walls[i][0]), getDrawYPos(walls[i][1]), getDrawXPos(walls[i][2]),
                        getDrawYPos(walls[i][3]));
            }
        }
    }

    // @param   x   X position of the point
    // @return  int X position of where to draw
    private int getDrawXPos(int x) {
        return x * blockSize + 10; // +10 because of Frame borders
    }

    // @param   y   Y position of the point
    // @return  int Y position of where to draw
    private int getDrawYPos(int y) {
        return y * blockSize + 35; // +35 because of Frame borders
    }
}
