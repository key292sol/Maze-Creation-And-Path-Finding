package generator;

import java.util.ArrayList;

import maze.*;

public class Sidewinder extends MazeGenerator {
    private ArrayList<Cell> currentPassage;
    private int curRow, curCol;

    public Sidewinder(int size) {
        this(size, size);
    }

    public Sidewinder(int row, int col) {
        super(row, col);
        currentPassage = new ArrayList<>();
        current = getCellAt(0, 0);
        curCol = 0;
        curRow = 0;
    }

    @Override
    public void nextIteration() {
        super.nextIteration();

        Cell last = current;
        nextPosition();
        if (hasEnded()) {
            setGridNotVisited();
            return;
        }

        current = getCellAt(curRow, curCol);
        colorCurrentCell(Maze.CURR_CELL_COLOR);
        if (current.row == 0) {
            // Remove left wall
            removeWall(last);
            return;
        }

        boolean carveToNextCell = random.nextBoolean();
        Cell nextCell = getCellAt(curRow, curCol + 1);
        currentPassage.add(current);

        if (carveToNextCell && nextCell != null) {
            removeWall(nextCell);
        } else {
            // Get a random cell from currentPassage
            int ind = random.nextInt(currentPassage.size());
            Cell randCell = currentPassage.get(ind);
            // Get the cell above the random cell
            Cell up = getCellAt(randCell.row - 1, randCell.col);
            // Remove walls
            removeWall(randCell, up);
            // Clear the current Passage
            currentPassage.clear();
            changedCells.add(randCell);
            changedCells.add(up);
        }
    }

    private void nextPosition() {
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
