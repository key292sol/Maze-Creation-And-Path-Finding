package solver;

import maze.*;


/*
 * Similar to DFS but we move to the neighbor with the least amount of distance to the destination
 */
public class OptimizedDfs extends MazeSolver {
    @Override
    public void nextIteration() {
        super.nextIteration();
        
        // If destination is reached then solving is finished
        // Else if the cell has no neighbors then we reached a dead end
        if (current == dest) {
            finished = true;
            return;
        }

        current.visited = true;
        calcNeighborsCost(current);
        Cell selected = getMinCostNeighbor();
        if (selected == null) {
            current = current.last;
            colorCurrentCell(Maze.CURR_CELL_COLOR);
            return;
        }

        selected.last = current;
        current = selected;
        colorCurrentCell(Maze.CURR_CELL_COLOR);
    }

    private void calcNeighborsCost(Cell cell) {
        for (Cell n : cell.neighbors) {
            if(!n.visited)
            n.cost = Math.abs(dest.row - n.row) + Math.abs(dest.col - n.col);
        }
    }

    private Cell getMinCostNeighbor() {
        int index = 0, minIndex = -1, minCost = Integer.MAX_VALUE;
        while (index < current.neighbors.size()) {
            Cell c = current.neighbors.get(index);
            if (!c.visited && c.cost < minCost) {
                minCost = c.cost;
                minIndex = index;
            }
            index++;
        }

        if (minIndex == -1) {
            return null;
        }
        return current.neighbors.get(minIndex);
    }
}
