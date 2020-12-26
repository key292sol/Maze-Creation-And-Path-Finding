package maze;

import java.util.ArrayList;
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

    public abstract Cell nextIteration();

    public class Cell {
        public boolean visited = false;
        public Color color = Color.WHITE;
        public boolean[] walls = { true, true, true, true }; // up right down left
        public int row, col;
        public ArrayList<Cell> neighbors = new ArrayList<>();

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
    
    public void setInitialNeighbors(Cell cell) {
        Cell[] potentials = { getCellAt(cell.row - 1, cell.col), getCellAt(cell.row, cell.col + 1), getCellAt(cell.row + 1, cell.col),
            getCellAt(cell.row, cell.col - 1), };
        for (Cell n : potentials) {
            if (n != null)
                cell.neighbors.add(n);
        }
    }

    public void setNeighbors(Cell cell) {
        cell.neighbors = new ArrayList<>();
        Cell[] potentials = { getCellAt(cell.row - 1, cell.col), getCellAt(cell.row, cell.col + 1), getCellAt(cell.row + 1, cell.col),
                getCellAt(cell.row, cell.col - 1), };
        for (int i = 0; i < potentials.length; i++) {
            if (!cell.walls[i]) {
                cell.neighbors.add(potentials[i]);
            }
        }
    }
        
    public Cell getRandomNeighbor(Cell cell) {
        Random random = new Random();
        boolean visitedN[] = new boolean[cell.neighbors.size()];
        int i = 0;
        while (i < cell.neighbors.size()) {
            visitedN[i] = cell.neighbors.get(i).visited;
            i++;
        }
        while (i > 0) {
            i--;
            if (visitedN[i]) {
                cell.neighbors.remove(i);
            }
        }
        if (cell.neighbors.size() == 0)
            return null;
        return cell.neighbors.remove(random.nextInt(cell.neighbors.size()));
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

    public void drawCell(Graphics g, Cell cell) {
        g.setColor(cell.color);
        g.fillRect(cell.col * blockSize + 10, cell.row * blockSize + 35, blockSize, blockSize);
        g.setColor(Color.BLACK);

        int[][] walls = {                                                               // x1, y1, x2, y2 positions
                { (cell.col),     (cell.row),     (cell.col + 1), (cell.row)     },     // top wall
                { (cell.col + 1), (cell.row),     (cell.col + 1), (cell.row + 1) },     // right wall
                { (cell.col),     (cell.row + 1), (cell.col + 1), (cell.row + 1) },     // bottom wall
                { (cell.col),     (cell.row),     (cell.col),     (cell.row + 1) }      // left wall
        };
        for (int i = 0; i < walls.length; i++) {
            if (cell.walls[i]) {
                g.drawLine(getDrawXPos(walls[i][0]), getDrawYPos(walls[i][1]), getDrawXPos(walls[i][2]),
                        getDrawYPos(walls[i][3]));
            }
        }
    }

    private int getDrawXPos(int x) {
        return x * blockSize + 10; // +10 because of Frame borders
    }

    private int getDrawYPos(int y) {
        return y * blockSize + 35; // +35 because of Frame borders
    }
}
