package main;

import java.awt.Graphics;
import java.lang.reflect.*;

import javax.swing.JFrame;

import generator.*;
import solver.*;
import maze.*;

public class DisplayFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private Chooser ch;

    private int rows, cols, delay;
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
            solver.drawCell(g, solver.current);
            if (solver.current.last == solver.start) endSolve();
            repaint();
        } else {
            if (mazeObj.current != solver.dest && mazeObj.current != solver.start) mazeObj.current.color = Maze.VISITED_COLOR;
            mazeObj.drawCell(g, mazeObj.current);
            mazeObj.nextIteration();
            if (!mazeObj.finished) {
                for (Cell c : mazeObj.changedCells) {
                    mazeObj.drawCell(g, c);
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
                mazeObj.drawCell(g, cell);
            }
        }
    }
}
