package solver;

import maze.*;

/*
 * Keep visiting nodes until we reach a dead end
 * After reaching dead end backtrack till a node with not visited neighbors
 * Repeat until destintion is found
 */
public class DepthFirstSearch extends MazeSolver {
    public Cell nextIteration() {
        Cell last  = current;
        if (last != start && last != dest) 
            last.color = Maze.VISITED_COLOR;

        // If destination is reached then solving is finished
        // Else if the cell has no neighbors then we reached a dead end
        if (current == dest) {
            finished = true;
            return null;
        } else if (current.neighbors.size() == 0) {
            current = current.last;
            colorCurrentCell();
            return last;
        }

        // Cell has unvisited neighbors
        // Go to the first neighbor
        Cell selected = current.neighbors.remove(0);
        selected.neighbors.remove(current);
        selected.last = current;

        current = selected;
        colorCurrentCell();

        return last;
    }
}

