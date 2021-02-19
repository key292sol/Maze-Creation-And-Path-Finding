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
        current.visited = true;

        // If destination is reached then solving is finished
        // If the cell has no neighbors then we reached a dead end
        if (current == dest) {
            finished = true;
            return;
        }

        int index = 0;
        while (index < current.neighbors.size() && current.neighbors.get(index).visited) {
            index++;
        }

        if (index == current.neighbors.size()) {
            current = current.last;
            colorCurrentCell(Maze.CURR_CELL_COLOR);
        } else {
            // Cell has unvisited neighbors
            // Go to the first neighbor
            Cell selected = current.neighbors.get(index);
            // selected.neighbors.remove(current);
            selected.last = current;
    
            current = selected;
            colorCurrentCell(Maze.CURR_CELL_COLOR);
        }
    }
}

