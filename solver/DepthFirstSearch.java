package solver;

import java.util.Stack;

import maze.Maze;

public class DepthFirstSearch extends MazeSolver {
    Stack<Cell> pathStack = new Stack<>();

    DepthFirstSearch(int size) {
        this(size, size);
    }

    DepthFirstSearch(int row, int col) {
        super(row, col);
        pathStack.push(current);
    }

    public Cell nextIteration() {
        if (!pathStack.empty()) {
            Cell last = current;
            if (last != null && last != start) last.color = Maze.visitedColor;
            current = pathStack.peek();
            if (current != start) current.color = Maze.visitedColor;
            
            if (current.neighbors.size() == 0) {
                pathStack.pop().color = currentCellColor;
                return last;
            }

            Cell selected = current.neighbors.remove(0);
            if (selected == dest) {
                finished = true;
                pathFound();
                return last;
            }

            selected.neighbors.remove(current);
            pathStack.push(selected);
            // current.color = Maze.visitedColor;
            selected.color = Maze.currentCellColor;
        }
        return null;
    }

    public void pathFound() {
        while (pathStack.size() > 1) {
            Cell cell = pathStack.pop();
            cell.isPath = true;
            cell.color = Maze.pathColor;
        }
        pathStack.pop();
    }
}

