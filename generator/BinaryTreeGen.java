package generator;

import maze.*;

/*
 * For each cell randomly decide whether to carve a passage to the left or up
 */
public class BinaryTreeGen extends MazeGenerator {
    int curRow, curCol;     

    public BinaryTreeGen(int size) {
        this(size, size);
    }

    public BinaryTreeGen(int row, int col) {
        super(row, col);
        curRow = 0;
        curCol = 0;
        current = getCellAt(0, 0);
        current.visited = true;
    }

    @Override
    public void nextIteration() {
        super.nextIteration();
        nextPosition();

        if (hasEnded()) {
            setGridNotVisited();
            return;
        }

        // Move to the cell and randomly choose whether to carve a passage to the left or not
        current = getCellAt(curRow, curCol);
        colorCurrentCell(Maze.CURR_CELL_COLOR);
        boolean removeLeft = random.nextBoolean();

        if (curCol != 0 && (curRow == 0 || removeLeft)) {
            // Carve passage to the left
            removeWall(getCellAt(curRow, curCol - 1));
        } else {
            // Carve passage to the cell above
            Cell upCell = getCellAt(curRow - 1, curCol);
            changedCells.add(upCell);
            removeWall(upCell);
        }
    }
    
    private void nextPosition() {
        // Move to the next Cell
        curCol++;
        if (curCol >= maze[0].length) {
            curRow++;
            curCol = 0;
        }
    }

    private boolean hasEnded() {
        return curRow >= maze.length;
    }
}
