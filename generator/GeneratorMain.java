package generator;

import maze.Maze;

import javax.swing.JFrame;
import java.awt.Graphics;

public class GeneratorMain extends JFrame {
    private static final long serialVersionUID = 1L;
    MazeGenerator generator;
    boolean started = false, finished = false, visualize = true;

    GeneratorMain() {
        setSize(Maze.frameWidth + 25, Maze.frameHeight + 50);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generator = new RecursiveBacktrackingAlgorithm(100);
        if (!visualize) generator.createWholeMaze();
        repaint();
    }

    public void paint(Graphics g) {
        if (generator != null && generator.maze != null) {
            // For showing whole maze
            if (!visualize) {
                drawWholeGrid(g);
            } 
            // For watching the maze creation cell by cell
            // Increase the time in Thread.sleep at the end of method to slow it down
            else if (!started) {
                started = true;
                drawWholeGrid(g);
                repaint();
            } else if (!finished) {
                Maze.Cell cell = generator.nextIteration();
                if (generator.current != null) {
                    generator.drawCell(g, cell);
                    generator.drawCell(g, generator.current);
                    repaint();
                } else {
                    finished = true;
                    drawWholeGrid(g);
                }
            }
        }
        try {
            // Thread.sleep(100);
        } catch (Exception e) {}
    }

    public void drawWholeGrid(Graphics g) {
        for (Maze.Cell[] cells : generator.maze) {
            for (Maze.Cell cell : cells) {
                generator.drawCell(g, cell);
            }
        }
    }

    public static void main(String[] args) {
        new GeneratorMain();
    }
}
