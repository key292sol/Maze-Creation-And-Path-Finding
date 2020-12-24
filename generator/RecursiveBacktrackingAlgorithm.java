package generator;

import java.util.Stack;

import maze.Maze;

public class RecursiveBacktrackingAlgorithm extends MazeGenerator {
    Stack<Cell> cellStack;

    RecursiveBacktrackingAlgorithm(int gridSize) {
        this(gridSize, gridSize);
    }

    public RecursiveBacktrackingAlgorithm(int row, int col) {
        super(row, col);
        cellStack = new Stack<>();
        cellStack.push(current);
        current.visited = true;
    }

    public void createWholeMaze() {
        while (!cellStack.isEmpty())
            nextIteration();
        
        setGridNotVisited();
    }

    public Cell nextIteration() {
        if (!cellStack.isEmpty()) {
            Cell last = current;
            last.color = Maze.visitedColor;
            current = cellStack.peek();
            Cell selected = current.getRandomNeighbor();
            if (selected == null) {
                cellStack.pop();
                current.color = Maze.currentCellColor;
                return last;
            }

            selected.neighbors.remove(current);
            selected.visited = true;
            selected.color = Maze.visitedColor;
            removeWall(selected);
            cellStack.push(selected);
            current = selected;
            current.color = Maze.currentCellColor;
            return last;
        }

        setGridNotVisited();
        return null;
    }
}

