package solver;

import java.util.PriorityQueue;

import generator.MazeGenerator;
import maze.*;

/*
 * AStar - A* path finder
 * Normally A* uses a priority queue
 * Steps:
 * 1. Start at source
 * 2. Calculate cost for each of its neighbors using formula:
 *    f = g + h
 *    Where g is the distance of cell from start
 *    And h is the distance of cell from destination
 * 3. Add the neighbors to the priority queue
 * 4. Remove and move to the element with the least f cost from priority queue
 * 5. Repeat steps 2 to 5 until destination is reached
 */
public class AStar extends MazeSolver {
    PriorityQueue<Cell> pqueue = new PriorityQueue<>();

    public void setGenerator(MazeGenerator gen) {
        super.setGenerator(gen);
        pqueue.add(current);
    }

    @Override
    public Cell nextIteration() {
        Cell last = current;
        if (last != start && last != dest) 
            last.color = Maze.VISITED_COLOR;
        
        current = pqueue.remove();
        
        if (current == dest) {
            finished = true;
            return null;
        } else {
            calcNeighborCost();
            colorCurrentCell();
        }

        return last;
    }

    private void calcNeighborCost() {
        for (Cell cell : current.neighbors) {
            // Calculate cost using x and y differences
            cell.gcost = Math.abs(cell.row - start.row) + Math.abs(cell.col - start.col);
            cell.hcost = Math.abs(cell.row - dest.row ) + Math.abs(cell.col - dest.col );

            // Calculate cost using slopes of the points
            // cell.gcost = getSlope(cell.row, cell.col, start.row, start.col);
            // cell.hcost = getSlope(cell.row, cell.col, dest.row,  dest.col );

            cell.fcost = cell.gcost + cell.hcost;
            cell.last = current;
            cell.neighbors.remove(current);     // So that we don't calculate the cost again in a future iteration
            pqueue.add(cell);
        }
    }

    private int getSlope(int r1, int c1, int r2, int c2) {
        int a = r1 - r2, b = c1 - c2;
        return (int) Math.sqrt((a*a) + (b*b));
    }
}
