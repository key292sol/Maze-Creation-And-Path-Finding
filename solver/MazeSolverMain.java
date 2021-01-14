package solver;

import javax.swing.JFrame;

import maze.*;

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

        int gridRows, gridCols = 20;
        gridRows = gridCols;
        solver = new BFS(gridRows);
        // solver = new DepthFirstSearch(gridRows);
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
            Thread.sleep(50);
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

