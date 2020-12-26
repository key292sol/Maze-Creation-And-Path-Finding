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

    /*
     * Keep visiting nodes until we reach a dead end
     * After reaching dead end backtrack till a node with not visited neighbors
     * Repeat until destintion is found
     */
    public Cell nextIteration() {
        Cell last  = current;
        if (last != start && last != dest) 
            last.color = Maze.VISITED_COLOR;

        if (current == dest) {
            finished = true;
            pathFound();
            return null;
        } else if (current.neighbors.size() == 0) {
            pathStack.pop();
            current = pathStack.peek();
            if (current != start && current != dest) current.color = Maze.CURR_CELL_COLOR;
            return last;
        }

        Cell selected = current.neighbors.remove(0);
        selected.neighbors.remove(current);
        pathStack.push(selected);
        
        current = selected;
        if (current != start && current != dest)
            current.color = Maze.CURR_CELL_COLOR;

        return last;
    }

    public void pathFound() {
        pathStack.pop(); // Popping the destintion node
        while (pathStack.size() > 1) {
            Cell cell = pathStack.pop();
            cell.color = Maze.PATH_COLOR;
        }
        pathStack.pop(); // Popping the start node from stack
    }
}

