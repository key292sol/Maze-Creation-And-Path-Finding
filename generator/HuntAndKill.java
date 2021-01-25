package generator;

import maze.*;

/*
 * This is similar to the Backtracker algorithm
 * But when this algorithm reaches a dead end, instead of backtracking, 
 * it hunts for a cell which has unvisited neighbors left
 */
public class HuntAndKill extends MazeGenerator {
    int curRow, curCol;

    public HuntAndKill(int size) {
        this(size, size);
    }

    public HuntAndKill(int row, int col) {
        super(row, col);
        curRow = 0;
        curCol = 0;
        current = getCellAt(0, 0);
    }

    public Cell nextIteration() {
    	Cell last = current;
        last.color = Maze.VISITED_COLOR;

        Cell selected = getRandomNeighbor(current);

        if (selected == null) {
            nextPosition();
            if (hasEnded()) {
                setGridNotVisited();
                return null;
            }
            current = getCellAt(curRow, curCol);
        } else {
            removeWall(selected);
            selected.visited = true;
            selected.neighbors.remove(current);
            current = selected;
        }

        current.color = Maze.CURR_CELL_COLOR;
        return last;
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