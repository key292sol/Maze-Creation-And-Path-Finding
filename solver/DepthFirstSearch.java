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
            if (current == dest) {
                finished = true;
                pathFound();
                return null;
            }
            if (current != start) current.color = Maze.currentCellColor;
            
            if (current.neighbors.size() == 0) {
                pathStack.pop().color = currentCellColor;
                return last;
            }

            Cell selected = current.neighbors.remove(0);

            selected.neighbors.remove(current);
            if (selected != dest) selected.color = Maze.visitedColor;
            pathStack.push(selected);
            return last;
        }
        return null;
    }

    public void pathFound() {
        pathStack.pop();
        while (pathStack.size() > 1) {
            Cell cell = pathStack.pop();
            cell.color = Maze.pathColor;
        }
        pathStack.pop();
    }
}

