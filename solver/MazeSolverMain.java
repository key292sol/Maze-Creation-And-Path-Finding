package solver;

import javax.swing.JFrame;

import maze.Maze;

import java.awt.Graphics;

public class MazeSolverMain extends JFrame {
    private static final long serialVersionUID = 1L;
    DepthFirstSearch dfs;
    Maze.Cell start;

    MazeSolverMain() {
        setSize(Maze.frameWidth + 25, Maze.frameHeight + 50);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int gridRows, gridCols = 15;
        gridRows = gridCols;
        dfs = new DepthFirstSearch(gridRows);
        repaint();
    }

    public void paint(Graphics g) {
        // For showing whole maze
        if (this.dfs == null) {
            repaint();
            return;
        }
        for (Maze.Cell[] cells : dfs.maze) {
            for (Maze.Cell cell : cells) {
                dfs.drawCell(g, cell);
            }
        }

        dfs.nextIteration();
        try {
            // Thread.sleep(50);
        } catch (Exception e) {
            
        }
        repaint();
    }

    public static void main(String[] args) {
        new MazeSolverMain();
    }
}

