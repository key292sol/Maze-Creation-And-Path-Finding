package maze;

import java.util.ArrayList;
import java.awt.Color;

public class Cell implements Comparable<Cell> {
    public boolean visited = false;
    public Color color = Maze.NOT_VISITED_COLOR;
    public boolean[] walls = { true, true, true, true }; // up right down left
    public int row, col;
    public ArrayList<Cell> neighbors = new ArrayList<>();
    public Cell last;
    public int fcost, gcost, hcost;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int compareTo(Cell other) {
        if (this.fcost > other.fcost) {
            return 1;
        } else if (this.fcost < other.fcost) {
            return -1;
        } else {
            if (this.hcost > other.hcost) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
