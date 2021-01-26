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
        } else if (current.neighbors.size() == 0) {
            current = current.last;
            colorCurrentCell(Maze.CURR_CELL_COLOR);
            return;
        }

        calcNeighborsCost(current);
        Cell selected = removeMinCostNeighbor();
        selected.neighbors.remove(current);
        selected.last = current;

        current = selected;
        colorCurrentCell(Maze.CURR_CELL_COLOR);
    }

    private void calcNeighborsCost(Cell cell) {
        for (Cell n : cell.neighbors) {
            n.cost = Math.abs(dest.row - n.row) + Math.abs(dest.col - n.col);
        }
    }

    private Cell removeMinCostNeighbor() {
        int minIndex = 0, minCost = current.neighbors.get(0).cost;
        for (int i = 1; i < current.neighbors.size(); i++) {
            if (current.neighbors.get(i).cost < minCost) {
                minCost = current.neighbors.get(i).cost;
                minIndex = i;
            }
        }
        return current.neighbors.remove(minIndex);
    }
}
