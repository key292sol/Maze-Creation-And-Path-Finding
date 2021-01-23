package main;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class Chooser extends JFrame {
    JSpinner rowSpinner, colSpinner, delaySpinner;
    JButton startGenBut, startFindBut, initBut;
    JComboBox<String> genChose, findChose;
    JCheckBox genCheck, findCheck;

    DisplayFrame df;

    public Chooser() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 3, 5, 5));
        setSize(400, 250);

        rowSpinner = new JSpinner(new SpinnerNumberModel(10, 5, 150, 1));
        colSpinner = new JSpinner(new SpinnerNumberModel(10, 5, 150, 1));
        delaySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 5, 0.5));

        initBut = new JButton("Set Values");
        startGenBut = new JButton("Generate Maze");
        startFindBut = new JButton("Find path");

        genCheck = new JCheckBox("Show generation", true);
        findCheck = new JCheckBox("Show solving", true);

        initGenChose();
        initFindChose();

        add(new JLabel("Rows"));        add(rowSpinner);    add(new JLabel());
        add(new JLabel("Columns"));     add(colSpinner);    add(new JLabel());
        add(new JLabel("Generator"));   add(genChose);      add(genCheck);
        add(new JLabel("Solver"));      add(findChose);     add(findCheck);
        add((new JLabel("Delay")));     add(delaySpinner);  add(new JLabel("seconds"));
        add(initBut);                   add(startGenBut);   add(startFindBut);

        setVisible(true);
        df = new DisplayFrame(this);
    }

    public void initGenChose() {
        genChose = new JComboBox<>();
        String vals[] = {"Backtracker", "Random Prim's"};
        for (String str : vals) {
            genChose.addItem(str);
        }
    }

    public void initFindChose() {
        findChose = new JComboBox<>();
        String vals[] = {"DFS", "BFS", "A*"};
        for (String str : vals) {
            findChose.addItem(str);
        }
    }
}
