package solver;

import javax.swing.JFrame;

import maze.Maze;
import maze.Maze.Cell;

import java.awt.Graphics;

public class MazeSolverMain extends JFrame {
    private static final long serialVersionUID = 1L;
    MazeSolver solver;
    Maze.Cell start;
    boolean started = false, finished = false;

    MazeSolverMain() {
        setSize(Maze.frameWidth + 25, Maze.frameHeight + 50);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int gridRows, gridCols = 20;
        gridRows = gridCols;
        solver = new DepthFirstSearch(gridRows);
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
            g.setColor(Maze.visitedColor);
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
            drawWholeGrid(g);
        }

        try {
            // Thread.sleep(50);
        } catch (Exception e) {
            
        }
        repaint();
    }

    public void drawWholeGrid(Graphics g) {
        for (Maze.Cell[] cells : solver.maze) {
            for (Maze.Cell cell : cells) {
                solver.drawCell(g, cell);
            }
        }
    }

    public static void main(String[] args) {
        new MazeSolverMain();
    }
}

