package main;

import java.awt.Graphics;
import java.awt.Color;
import java.lang.reflect.*;

import javax.swing.JFrame;

import generator.*;
import solver.*;
import maze.*;

public class DisplayFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private Chooser ch;

    private int rows, cols, delay;
    private int blockSize;
    private boolean gridDrawn, genCompleted, findCompleted;
    private boolean visualizeGen, visualizeFind, visualize;

    private MazeGenerator generator;
    private MazeSolver solver;
    private Maze mazeObj;

    public DisplayFrame(Chooser ch) {
        this.ch = ch;
        setSize(Maze.frameWidth + 25, Maze.frameHeight + 50);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init() {
        rows = (Integer)ch.rowSpinner.getValue();
        cols = (Integer)ch.colSpinner.getValue();
        double d = (Double)ch.delaySpinner.getValue();
        delay = (int) (d * 1000);
        visualizeGen = ch.genCheck.isSelected();
        visualizeFind = ch.findCheck.isSelected();

        gridDrawn = false;
        genCompleted = false;
        findCompleted = false;
        visualize = visualizeGen;

        // The blockSize shouldn't be less then 5
        blockSize = Math.min(Maze.frameHeight, Maze.frameWidth) / Math.max(rows, cols);
        blockSize = (blockSize < 5) ? 5 : blockSize;

        try {
            Class<?> class1 = Class.forName(Options.getGenClassFromIndex(ch.genChose.getSelectedIndex()));
            Constructor<?> con = class1.getConstructor(int.class, int.class);
            generator = (MazeGenerator) con.newInstance(rows, cols);

            class1 = Class.forName(Options.getSolveClassFromIndex(ch.findChose.getSelectedIndex()));
            con = class1.getConstructors()[0];
            if (con.getParameterCount() == 0) {
                solver = (MazeSolver) con.newInstance();
            } else {
                solver = (MazeSolver) con.newInstance(Options.isConstructorValTrue(ch.findChose.getSelectedIndex()));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        mazeObj = generator;
        repaint();
    }

    public void doDraw() {
        repaint();
    }

    private void endGen() {
        visualize = visualizeFind;
        genCompleted = true;
        ch.genComplete();
        solver.setGenerator(generator);
        mazeObj = solver;
    }

    private void endSolve() {
        findCompleted = true;
    }

    public void paint(Graphics g) {
        if (mazeObj == null || findCompleted) return;

        if (!gridDrawn) {
            gridDrawn = true;
            super.paint(g);
            if (!visualizeGen) {
                generator.completeAllIterations();
                endGen();
            }
            drawWholeGrid(g);
            return;
        }

        if (!visualize) {
            mazeObj.completeAllIterations();
            if (!genCompleted) {
                endGen();
            } else {
                endSolve();
            }
            drawWholeGrid(g);
            return;
        }

        if (genCompleted && solver.finished) {
            solver.lastPathCell();
            drawCell(g, solver.current);
            if (solver.current.last == solver.start) endSolve();
            repaint();
        } else {
            if (mazeObj.current != solver.dest && mazeObj.current != solver.start) mazeObj.current.color = Maze.VISITED_COLOR;
            drawCell(g, mazeObj.current);
            mazeObj.nextIteration();
            if (!mazeObj.finished) {
                for (Cell c : mazeObj.changedCells) {
                    drawCell(g, c);
                }
                repaint();
            } else if (!genCompleted) {
                endGen();
                drawWholeGrid(g);
            } else {
                repaint();
            }
        }

        try {
            Thread.sleep(delay);
        } catch (Exception e) {
        }
    }

    public void drawWholeGrid(Graphics g) {
        for (Cell[] cells: mazeObj.maze) {
            for (Cell cell : cells) {
                drawCell(g, cell);
            }
        }
    }

    /*
     * @param   g       The Graphics object on which you need to draw cell
     * @param   cell    The cell needed to be drawn
     */
    public void drawCell(Graphics g, Cell cell) {
        // Draw the rectangle/square shape of the cell
        g.setColor(cell.color);
        g.fillRect(getDrawXPos(cell.col) + 1, getDrawYPos(cell.row) + 1, blockSize, blockSize);

        // Draw walls
        g.setColor(Color.BLACK);
        int[][] walls = {                                                               // x1, y1, x2, y2 positions
                { (cell.col),     (cell.row),     (cell.col + 1), (cell.row)     },     // top wall
                { (cell.col + 1), (cell.row),     (cell.col + 1), (cell.row + 1) },     // right wall
                { (cell.col),     (cell.row + 1), (cell.col + 1), (cell.row + 1) },     // bottom wall
                { (cell.col),     (cell.row),     (cell.col),     (cell.row + 1) }      // left wall
        };

        // If there exists a wall there, then draw a line
        // representing the wall
        for (int i = 0; i < walls.length; i++) {
            if (cell.walls[i]) {
                g.drawLine(getDrawXPos(walls[i][0]), getDrawYPos(walls[i][1]), getDrawXPos(walls[i][2]),
                        getDrawYPos(walls[i][3]));
            }
        }
    }

    // @param   x   X position of the point
    // @return  int X position of where to draw
    private int getDrawXPos(int x) {
        return x * blockSize + 10; // +10 because of Frame borders
    }

    // @param   y   Y position of the point
    // @return  int Y position of where to draw
    private int getDrawYPos(int y) {
        return y * blockSize + 35; // +35 because of Frame borders
    }
}
