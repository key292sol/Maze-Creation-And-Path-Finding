package solver;

import java.util.Random;
import java.util.Stack;

import generator.*;
import maze.Maze;

public class DepthFirstSearch extends Maze {
    Stack<Cell> pathStack = new Stack<>();
    Cell start, dest;
    boolean finished = false;

    DepthFirstSearch(int size) {
        this(size, size);
    }

    DepthFirstSearch(int row, int col) {
        MazeGenerator gen = new RecursiveBacktrackingAlgorithm(row, col);
        gen.createWholeMaze();
        this.blockSize = gen.blockSize;
        this.maze = gen.maze;
        
        Random random = new Random();
        int startRow = random.nextInt(row), destRow = random.nextInt(row);
        int startCol = random.nextInt(col), destCol = random.nextInt(col);
        start = getCellAt(startRow, startCol);
        start.color = Maze.startCell;
        pathStack.push(start);
        dest = getCellAt(destRow, destCol);
        dest.color = Maze.destColor;
    }

    public void nextIteration() {
        if (!pathStack.empty()) {
            Cell last = current;
            if (last != null && last != start) last.color = Maze.visitedColor;
            current = pathStack.peek();
            if (current != start) current.color = Maze.visitedColor;
            
            if (current.neighbors.size() == 0) {
                pathStack.pop().color = currentCellColor;
                return;
            }

            Cell selected = current.neighbors.remove(0);
            if (selected == dest) {
                finished = true;
                pathFound();
                return;
            }

            selected.neighbors.remove(current);
            pathStack.push(selected);
            // current.color = Maze.visitedColor;
            selected.color = Maze.currentCellColor;
        }
    }

    public void pathFound() {
        while (!pathStack.empty()) {
            Cell cell = pathStack.pop();
            cell.isPath = true;
            cell.color = Maze.pathColor;
        }
    }
}

