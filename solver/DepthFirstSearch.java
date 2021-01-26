package solver;

import maze.*;

/*
 * Keep visiting nodes until we reach a dead end
 * After reaching dead end backtrack till a node with not visited neighbors
 * Repeat until destintion is found
 */
public class DepthFirstSearch extends MazeSolver {
    public void nextIteration() {
        super.nextIteration();

        // If destination is reached then solving is finished
        // Else if the cell has no neighbors then we reached a dead end
        if (current == dest) {
            finished = true;
            return;
        } else if (current.neighbors.size() == 0) {
            current = current.last;
            colorCurrentCell(Maze.CURR_CELL_COLOR);
            return;
        }

        // Cell has unvisited neighbors
        // Go to the first neighbor
        Cell selected = current.neighbors.remove(0);
        selected.neighbors.remove(current);
        selected.last = current;

        current = selected;
        colorCurrentCell(Maze.CURR_CELL_COLOR);
    }
}

