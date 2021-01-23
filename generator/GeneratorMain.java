package generator;

import maze.*;

import javax.swing.JFrame;
import java.awt.Graphics;

public class GeneratorMain extends JFrame {
    private static final long serialVersionUID = 1L;
    MazeGenerator generator;
    boolean started = false, finished = false, visualize = !true;

    GeneratorMain() {
        // +25 and +50 because of Frame borders
        setSize(Maze.frameWidth + 25, Maze.frameHeight + 50);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // generator = new RecursiveBacktrackingAlgorithm(20);
        generator = new RandomPrims(10);
        if (!visualize)
            generator.completeAllIterations();
        repaint();
    }

    public void paint(Graphics g) {
        if (generator == null || generator.maze == null) {
            repaint();
            return;
        }

        /*
         * If visualization is false then maze has been created and just need to be
         * displayed Else: Draw the whole grid before starting the maze creation For
         * every iteration draw the current cell and the last cell If the whole maze is
         * created then display the whole maze
         */

        if (!visualize) {
            drawWholeGrid(g);
        } else if (!started) {
            started = true;
            drawWholeGrid(g);
            repaint();
        } else if (!finished) {
            Cell cell = generator.nextIteration();
            if (generator.current != null && cell != null) {
                generator.drawCell(g, cell);
                generator.drawCell(g, generator.current);
                repaint();
            } else {
                finished = true;
                drawWholeGrid(g);
            }
        }

        // Thread.sleep to slow down the visualization of maze creation
        try {
            Thread.sleep(1);
        } catch (Exception e) {
        }
    }

    public void drawWholeGrid(Graphics g) {
        for (Cell[] cells : generator.maze) {
            for (Cell cell : cells) {
                generator.drawCell(g, cell);
            }
        }
    }

    public static void main(String[] args) {
        new GeneratorMain();
    }
}
