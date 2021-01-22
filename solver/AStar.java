package solver;

import java.util.PriorityQueue;

import generator.MazeGenerator;
import maze.*;

public class AStar extends MazeSolver {
    PriorityQueue<Cell> pqueue = new PriorityQueue<>();

    public void setGenerator(MazeGenerator gen) {
        super.setGenerator(gen);
        pqueue.add(current);
    }

    public Cell nextIteration() {
        Cell last = current;
        if (last != start && last != dest) 
            last.color = Maze.VISITED_COLOR;
        
        if (current == dest) {
            finished = true;
            return null;
        } else {
            calcNeighborCost();
            current = pqueue.remove();
            if (current != start && current != dest)
                current.color = Maze.CURR_CELL_COLOR;
        }

        return last;
    }

    private void calcNeighborCost() {
        if (current.neighbors.size() == 0) {
            return;
        }
        for (Cell cell : current.neighbors) {
            cell.gcost = Math.abs(current.row - start.row) + Math.abs(current.col - start.col);
            cell.hcost = Math.abs(current.row - dest.row ) + Math.abs(current.col - dest.col );
            // cell.gcost = getSlope(current.row, current.col, start.row, start.col);
            // cell.hcost = getSlope(current.row, current.col, dest.row,  dest.col );
            cell.fcost = cell.gcost + cell.hcost;
            cell.last = current;
            cell.neighbors.remove(current);
            pqueue.add(cell);
        }
        current.neighbors.clear();
    }

    public int getSlope(int r1, int c1, int r2, int c2) {
        int a = r1 - r2, b = c1 - c2;
        return (int) Math.sqrt((a*a) + (b*b));
    }

    public void pathFound() {

    }
    
}
