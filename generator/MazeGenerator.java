package generator;

import maze.*;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public abstract class MazeGenerator extends Maze {
    Random random = new Random();

    public MazeGenerator(int gridSize) {
        this(gridSize, gridSize);
    }

    public MazeGenerator(int row, int col) {

        // Initializing the maze
        maze = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze[i][j] = new Cell(i, j);
            }
        }

        // Setting neighbors
        for (Cell[] cells : maze) {
            for (Cell cell : cells) {
                setInitialNeighbors(cell);
            }
        }

        current = getRandomCell(row, col);
    }

    /*
     * For initilizing neighbors to all the cells in its neighbors
     * without checking if they have a wall between them or not.
     * Used to set neighbors before maze generation
     * @param   cell    The cell whose neighbors are to be initialized
     */
    public void setInitialNeighbors(Cell cell) {
        Cell[] potentials = { getCellAt(cell.row - 1, cell.col), getCellAt(cell.row, cell.col + 1), getCellAt(cell.row + 1, cell.col),
            getCellAt(cell.row, cell.col - 1), };
        for (Cell n : potentials) {
            if (n != null)
                cell.neighbors.add(n);
        }
    }
    
    // @param   cell    The cell whose neighbor is to be returned
    // @return  Cell    A random neighbor or null if no neighbors are left unvisited
    public Cell getRandomNeighbor(Cell cell) {
        // If a neighbor has been visited then remove it
        // We don't need to go to alreaady visited neighbors
        for (int i = cell.neighbors.size() - 1; i >= 0; i--) {
            if (cell.neighbors.get(i).visited) {
                cell.neighbors.remove(i);
            }
        }

        // If no neighbors left return null
        if (cell.neighbors.size() == 0)
            return null;
        return cell.neighbors.remove(random.nextInt(cell.neighbors.size()));
    }

    // Used to set neighbors after completing the generation
    // @param   cell    The cell whose neighbors are to be initialized
    public void setNeighbors(Cell cell) {
        cell.neighbors = new ArrayList<>();
        Cell[] potentials = { getCellAt(cell.row - 1, cell.col), getCellAt(cell.row, cell.col + 1), getCellAt(cell.row + 1, cell.col),
                getCellAt(cell.row, cell.col - 1), };

        // If there exists no wall between the 2 cells then add them as neighbors
        for (int i = 0; i < potentials.length; i++) {
            if (!cell.walls[i]) {
                cell.neighbors.add(potentials[i]);
            }
        }
    }

    @Override
    public void colorCurrentCell(Color color) {
        current.color = color;
        changedCells.add(current);
    }

    @Override
    public void completeAllIterations() {
        super.completeAllIterations();
        setGridNotVisited();
    }

    // Setting maze to not visited for solving the maze afterwards
    public void setGridNotVisited() {
        finished = true;
        for (Cell[] cells : maze) {
            for (Cell cell : cells) {
                cell.visited = false;
                cell.color   = Maze.NOT_VISITED_COLOR;
                cell.last    = null;
                setNeighbors(cell);
            }
        }
    }

    // Remove wall between current and the passed Cell
    // Checking order acc to this.current cell: Top Right Bottom Left
    // @param   to  The neighbor of current cell
    public void removeWall(Cell to) {
        removeWall(current, to);
    }

    // Remove wall between the 2 cells
    // Checking order acc to the from cell: Top Right Bottom Left
    // @param   from    The first cell
    // @param   to      The neighbor of the first cell
    public void removeWall(Cell from, Cell to) {
        if (from.row > to.row) {
            from.walls[0] = false;
            to.walls[2]   = false;
        } else if (from.col < to.col) {
            from.walls[1] = false;
            to.walls[3]   = false;
        } else if (from.row < to.row) {
            from.walls[2] = false;
            to.walls[0]   = false;
        } else if (from.col > to.col) {
            from.walls[3] = false;
            to.walls[1]   = false;
        }
    }
}
