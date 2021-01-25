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
    PriorityQueue<AStarCost> pqueue = new PriorityQueue<>();
    private boolean useEuclidean;

    public AStar(boolean euclidean) {
        useEuclidean = euclidean;
    }

    public void setGenerator(MazeGenerator gen) {
        super.setGenerator(gen);
        AStarCost curCost = new AStarCost(current);
        pqueue.add(curCost);
    }

    @Override
    public Cell nextIteration() {
        Cell last = current;
        if (last != start && last != dest) 
            last.color = Maze.VISITED_COLOR;
        
        AStarCost curCost = pqueue.remove();
        current = getCellAt(curCost.row, curCost.col);
        
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
        AStarCost cost;
        for (Cell cell : current.neighbors) {
            // Cost object for Priority queue
            cost = new AStarCost(cell);

            if (!useEuclidean) {
                cost.calcManhattanCost();
            } else {
                cost.calcEuclidianCost();
            }

            cell.cost = cost.fcost;
            cell.last = current;
            cell.neighbors.remove(current);     // So that we don't calculate the cost again in a future iteration
            pqueue.add(cost);
        }
    }

    // For stooring the costs of cells
    class AStarCost implements Comparable<AStarCost> {
        int row, col;
        int gcost, hcost, fcost;
    
        AStarCost(Cell me) {
            this.row = me.row;
            this.col = me.col;
        }

        // The x distance + y distance
        private void calcManhattanCost() {
            this.gcost = Math.abs(this.row - start.row) + Math.abs(this.col - start.col);
            this.hcost = Math.abs(this.row - dest.row ) + Math.abs(this.col - dest.col );
            this.fcost = gcost + hcost;
        }

        // Adding the length of straight lines
        private void calcEuclidianCost() {
            this.gcost = getStraightDistance(this.row, this.col, start.row, start.col);
            this.hcost = getStraightDistance(this.row, this.col, dest.row,  dest.col );
            this.fcost = gcost + hcost;
        }

        private int getStraightDistance(int r1, int c1, int r2, int c2) {
            int a = r1 - r2, b = c1 - c2;
            return (int) Math.sqrt((a*a) + (b*b));
        }
    
        // compareTo() method of Comparable interface
        @Override
        public int compareTo(AStarCost other) {
            if (this.fcost > other.fcost) {
                return 1;
            } else if (this.fcost < other.fcost) {
                return -1;
            } else {
                if (this.hcost > other.hcost) {
                    return 1;
                } else if (this.hcost < other.hcost) {
                    return -1;
                } else {
                    if (this.gcost > other.gcost) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            }
        }
    }
}
