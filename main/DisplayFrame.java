package main;

import java.awt.Graphics;

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

        switch (ch.genChose.getSelectedIndex()) {
            case 0: 
                generator = new RecursiveBacktrackingAlgorithm(rows, cols);
                break;
            case 1: 
                generator = new RandomPrims(rows, cols);
                break;
            case 2:
                generator = new HuntAndKill(rows, cols);
                break;
            default:
                System.out.println("How a default in generator ?");
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
                solver = new OptimizedDfs();
                break;
            case 3:
                solver = new AStar(false);
                break;
            case 4:
                solver = new AStar(true);
                break;
            default:
                System.out.println("How a default in solver ?");
                return;
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
