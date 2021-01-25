package main;

import java.awt.Graphics;

import javax.swing.JFrame;

import generator.*;
import solver.*;
import maze.*;

public class DisplayFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    Chooser ch;

    private int rows, cols, delay;
    private boolean visualizeGen, visualizeFind;
    private boolean genStarted, genEnded;
    private boolean findStarted, findEnded;

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

        genStarted = false;
        genEnded = false;
        findStarted = false;
        findEnded = false;

        switch (ch.genChose.getSelectedIndex()) {
            case 0: 
                generator = new RecursiveBacktrackingAlgorithm(rows, cols);
                break;
            case 1: 
                generator = new RandomPrims(rows, cols);
                break;
            default:
                System.out.println("How a default generator ?");
                return;
        }

        switch (ch.findChose.getSelectedIndex()) {
            case 0:
                solver = new DepthFirstSearch();
                break;
            case 1:
                solver = new BFS();
                break;
            case 2:
                solver = new AStar(false);
                break;
            case 3:
                solver = new AStar(true);
                break;
        }

        mazeObj = generator;
        repaint();
    }

    public void startGeneration() {
        genStarted = true;
        repaint();
    }

    public void startSolving() {
        findStarted = true;
        repaint();
    }

    private void endGen() {
        ch.genComplete();
        solver.setGenerator(generator);
        mazeObj = solver;
    }

    public void paint(Graphics g) {
        if (mazeObj == null) return;
        if (!genStarted) {
            super.paint(g);
            drawWholeGrid(g);
            return;
        }

        if (!genEnded) {
            if (!visualizeGen) {
                generator.completeAllIterations();
                endGen();
                genEnded = true;
                drawWholeGrid(g);
                return;
            }
            Cell cell = generator.nextIteration();
            if (generator.current != null && cell != null) {
                generator.drawCell(g, cell);
                generator.drawCell(g, generator.current);
            } else {
                endGen();
                genEnded = true;
                drawWholeGrid(g);
            }
        } else if (findStarted && !findEnded) {
            if (visualizeFind) {
                if (!solver.finished) {
                    g.setColor(Maze.VISITED_COLOR);
                    solver.drawCell(g, solver.current);
                    Cell last = solver.nextIteration();
                    if (last != null) {
                        solver.drawCell(g, last);
                        solver.drawCell(g, solver.current);
                    }
                } else {
                    solver.lastPathCell();
                    solver.drawCell(g, solver.current);
                }
            } else {
                solver.completeAllIterations();
                ch.startFindBut.setEnabled(false);
                drawWholeGrid(g);
                findEnded = true;
            }
        }

        if (!findEnded) repaint();

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
