package maze;

import java.util.ArrayList;
import java.awt.Color;

public class Cell {
    public boolean visited = false;
    public Color color = Maze.NOT_VISITED_COLOR;
    public boolean[] walls = { true, true, true, true }; // Directions: up right down left
    public int row, col;
    public ArrayList<Cell> neighbors = new ArrayList<>();
    public Cell last;
    public int cost;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
