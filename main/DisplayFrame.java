package main;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.lang.reflect.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import generator.*;
import solver.*;
import maze.*;

public class DisplayFrame extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int PADDING = 10;

    private String imgName;

    private Chooser ch;
    private Timer repaintTimer;

    private int blockSize;
    private boolean draw, gridDrawn, genCompleted;
    private boolean visualizeGen, visualizeFind, visualize;

    private MazeGenerator generator;
    private MazeSolver solver;
    private Maze mazeObj;

    public DisplayFrame(Chooser ch) {
        this.ch = ch;
        draw = false;
        repaintTimer = new Timer(0, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                repaint();
            }
        });
        // repaintTimer.setRepeats(false);
    }

    public void init() {
        int rows  = (Integer) ch.rowSpinner.getValue();
        int cols  = (Integer) ch.colSpinner.getValue();
        blockSize = (Integer) ch.blockSizeSpinner.getValue();
        double d  = (Double)  ch.delaySpinner.getValue();
        repaintTimer.setDelay((int) (d * 1000));
        visualizeGen  = ch.genCheck.isSelected();
        visualizeFind = ch.findCheck.isSelected();
        imgName = ch.imageName.getText().trim();

        draw = true;
        gridDrawn = false;
        genCompleted = false;
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
        setPreferredSize(new Dimension(getDrawXPos(cols) + PADDING, getDrawYPos(rows) + PADDING));
        revalidate();
        repaint();
    }

    public void doDraw() {
        draw = true;
        repaintTimer.start();
        repaint();
    }

    private void endGen() {
        repaintTimer.stop();
        visualize = visualizeFind;
        genCompleted = true;
        ch.genComplete();
        solver.setGenerator(generator);
        mazeObj = solver;
        draw = false;
        makeImage("-gen");
    }

    private void endSolve() {
        repaintTimer.stop();
        draw = false;
        makeImage("-solved");
    }

    private void makeImage(String suffix) {
        if (imgName.length() != 0) {
            try {
                BufferedImage img = new BufferedImage(getDrawXPos(mazeObj.maze[0].length) + PADDING, getDrawYPos(mazeObj.maze.length) + PADDING, BufferedImage.TYPE_INT_RGB);
                Graphics g = img.getGraphics();
                drawWholeGrid(g);
                File opFile = new File(imgName + suffix + ".png");
                ImageIO.write(img, "png", opFile);
            } catch (Exception e) {
                System.out.println("Exception occured while creating image");
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (mazeObj == null) return;
        
        super.paintComponent(g);
        if (!draw) {
            drawWholeGrid(g);
            return;
        }
        if (!gridDrawn) {
            gridDrawn = true;
            if (!visualizeGen) {
                generator.completeAllIterations();
                endGen();
            }
            draw = false;
        } else if (!visualize) {
            mazeObj.completeAllIterations();
            if (!genCompleted) {
                endGen();
            } else {
                endSolve();
            }
        } else if (genCompleted && solver.finished) {
            solver.lastPathCell();
            if (solver.current.last == solver.start) endSolve();
            // repaintTimer.start();
        } else {
            mazeObj.nextIteration();
            if (!mazeObj.finished) {
                // repaintTimer.start();
            } else if (!genCompleted) {
                endGen();
            } else {
                // repaintTimer.start();
            }
        }

        drawWholeGrid(g);
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
        g.fillRect(getDrawXPos(cell.col), getDrawYPos(cell.row), blockSize, blockSize);

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
        return x * blockSize + PADDING;
    }

    // @param   y   Y position of the point
    // @return  int Y position of where to draw
    private int getDrawYPos(int y) {
        return y * blockSize + PADDING;
    }
}
