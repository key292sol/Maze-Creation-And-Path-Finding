package generator;

import maze.*;

/*
 * This is similar to the Backtracker algorithm
 * But when this algorithm reaches a dead end, instead of backtracking, 
 * it hunts for a cell which has unvisited neighbors left
 */
public class HuntAndKill extends MazeGenerator {
    int curRow, curCol;     // For storing the last cell in the hunt

    public HuntAndKill(int size) {
        this(size, size);
    }

    public HuntAndKill(int row, int col) {
        super(row, col);
        curRow = 0;
        curCol = 0;
        current = getCellAt(0, 0);
        current.visited = true;
    }

    @Override
    public void nextIteration() {
        super.nextIteration();

        Cell selected = getRandomNeighbor(current);

        if (selected == null) {
            nextPosition();
            if (hasEnded()) {
                setGridNotVisited();
                return;
            }
            current = getCellAt(curRow, curCol);
        } else {
            removeWall(selected);
            selected.visited = true;
            selected.neighbors.remove(current);
            current = selected;
        }

        colorCurrentCell(Maze.CURR_CELL_COLOR);
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