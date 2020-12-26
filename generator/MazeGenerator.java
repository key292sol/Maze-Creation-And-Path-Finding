package generator;

import maze.Maze;

import java.util.Random;

public abstract class MazeGenerator extends Maze {
    Random random = new Random();

    MazeGenerator(int gridSize) {
        this(gridSize, gridSize);
    }

    MazeGenerator(int row, int col) {
        // The blockSize shouldn't be less then 5
        int maxLength = Math.min(Maze.frameHeight, Maze.frameWidth) / 5;
        if (Math.max(row, col) < maxLength)
            blockSize = Math.min(Maze.frameHeight, Maze.frameWidth) / Math.max(row, col);
        else
            blockSize = 5;

        maze = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze[i][j] = new Cell(i, j);
            }
        }

        for (Cell[] cells : maze) {
            for (Cell cell : cells) {
                setInitialNeighbors(cell);
            }
        }

        current = getRandomCell(row, col);
    }

    public abstract void createWholeMaze();

    public void setGridNotVisited() {
        current = null;
        for (Cell[] cells : maze) {
            for (Cell cell : cells) {
                cell.visited = false;
                cell.color   = Maze.NOT_VISITED_COLOR;
                setNeighbors(cell);
            }
        }
    }

    public void removeWall(Cell to) {
        // Checking which wall to remove
        // OrdeChecking order acc to this.current cell: Top Right Bottom Left
        if (current.row > to.row) {
            current.walls[0] = false;
            to.walls[2]      = false;
        } else if (current.col < to.col) {
            current.walls[1] = false;
            to.walls[3]      = false;
        } else if (current.row < to.row) {
            current.walls[2] = false;
            to.walls[0]      = false;
        } else if (current.col > to.col) {
            current.walls[3] = false;
            to.walls[1]      = false;
        }
    }
}
