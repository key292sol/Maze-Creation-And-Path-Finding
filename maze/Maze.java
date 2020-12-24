package maze;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

public class Maze {
    public static int frameWidth = 760, frameHeight = 760;
    public static Color visitedColor     = Color.LIGHT_GRAY;
    public static Color notVisitedColor  = Color.WHITE;
    public static Color currentCellColor = Color.YELLOW;
    public static Color pathColor        = Color.GREEN;
    public static Color startCell        = Color.RED;
    public static Color destColor        = Color.BLUE;

    public Cell[][] maze;
    public Cell current;
    public int blockSize;

    public class Cell {
        public boolean visited = false, isPath = false;
        public Color color = Color.WHITE;
        public boolean[] walls = { true, true, true, true }; // up right down left
        public int row, col;
        public ArrayList<Cell> neighbors = new ArrayList<>();

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void setNeighbors() {
            Cell[] potentials = { getCellAt(row - 1, col), getCellAt(row, col + 1), getCellAt(row + 1, col),
                    getCellAt(row, col - 1), };
            for (int i = 0; i < potentials.length; i++) {
                if (!walls[i]) {
                    this.neighbors.add(potentials[i]);
                }
            }
        }

        public void setInitialNeighbors() {
            Cell[] potentials = { getCellAt(row - 1, col), getCellAt(row, col + 1), getCellAt(row + 1, col),
                    getCellAt(row, col - 1), };
            for (Cell cell : potentials) {
                if (cell != null)
                    neighbors.add(cell);
            }
        }

        public Cell getRandomNeighbor() {
            Random random = new Random();
            boolean visitedN[] = new boolean[neighbors.size()];
            int i = 0;
            while (i < neighbors.size()) {
                visitedN[i] = neighbors.get(i).visited;
                i++;
            }
            while (i > 0) {
                i--;
                if (visitedN[i]) {
                    neighbors.remove(i);
                }
            }
            if (neighbors.size() == 0)
                return null;
            return neighbors.remove(random.nextInt(neighbors.size()));
        }
    }

    public Cell getCellAt(int r, int c) {
        if (r < 0 || c < 0 || r >= maze.length || c >= maze[0].length)
            return null;
        return maze[r][c];
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
