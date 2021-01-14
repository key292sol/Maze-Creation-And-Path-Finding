package solver;

import javax.swing.JFrame;

import maze.*;
import generator.*;

import java.awt.Graphics;

public class MazeSolverMain extends JFrame {
    private static final long serialVersionUID = 1L;
    MazeSolver solver;
    Cell start;
    boolean started = false, finished = false;

    MazeSolverMain() {
        // +25 and +50 because of Frame borders
        setSize(Maze.frameWidth + 25, Maze.frameHeight + 50);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int gridRows, gridCols = 152;
        gridRows = gridCols;

        MazeGenerator gen;
        gen = new RandomPrims(gridRows);
        gen.createWholeMaze();

        // solver = new DepthFirstSearch();
        solver = new BFS();

        solver.setGenerator(gen);
        repaint();
    }

    public void paint(Graphics g) {
        if (this.solver == null) {
            repaint();
            return;
        }

        if (!started) {
            started = true;
            drawWholeGrid(g);
            repaint();
        } else if (!finished) {
            g.setColor(Maze.VISITED_COLOR);
            solver.drawCell(g, solver.current);
            Cell last = solver.nextIteration();
            if (last != null) {
                solver.drawCell(g, last);
                solver.drawCell(g, solver.current);
            } else {
                finished = true;
            }
            repaint();
        } else {
            solver.lastPathCell();
            solver.drawCell(g, solver.current);
            if (solver.current.last != null) repaint();
        }

        try {
            // Thread.sleep(1);
        } catch (Exception e) {
            
        }
    }

    public void drawWholeGrid(Graphics g) {
        for (Cell[] cells : solver.maze) {
            for (Cell cell : cells) {
                solver.drawCell(g, cell);
            }
        }
    }

    public static void main(String[] args) {
        new MazeSolverMain();
    }
}

