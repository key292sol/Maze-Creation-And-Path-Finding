package solver;

import maze.*;

public class DepthFirstSearch extends MazeSolver {
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
            return null;
        } else if (current.neighbors.size() == 0) {
            current = current.last;
            if (current != start && current != dest) current.color = Maze.CURR_CELL_COLOR;
            return last;
        }

        Cell selected = current.neighbors.remove(0);
        selected.neighbors.remove(current);
        selected.last = current;

        current = selected;
        if (current != start && current != dest)
            current.color = Maze.CURR_CELL_COLOR;

        return last;
    }

    public void pathFound() {
        current = current.last;                             // Popping the destintion node
        while (current.last != null) {
            current.color = Maze.PATH_COLOR; // Popping each node in path and changing its color
            current = current.last;
        }
    }
}

