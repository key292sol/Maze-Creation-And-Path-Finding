package main;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class Chooser extends JPanel {
    private static final long serialVersionUID = 1L;

    private DisplayFrame df;
    
    public JSpinner rowSpinner, colSpinner, blockSizeSpinner, delaySpinner;
    public JButton startGenBut, startFindBut, initBut;
    public JComboBox<String> genChose, findChose;
    public JCheckBox genCheck, findCheck;
    public JTextField imageName;


    public Chooser() {
        setLayout(new GridLayout(8, 3, 5, 5));
        MyActionListener mal = new MyActionListener();

        rowSpinner = new JSpinner(new SpinnerNumberModel(10, 5, 1000, 1));
        colSpinner = new JSpinner(new SpinnerNumberModel(10, 5, 1000, 1));
        blockSizeSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
        delaySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 5, 0.01));

        initBut = new JButton("Set Values");
        startGenBut = new JButton("Generate Maze");
        startFindBut = new JButton("Find path");

        genCheck = new JCheckBox("Show generation", true);
        findCheck = new JCheckBox("Show solving", true);

        imageName = new JTextField();

        initGenChose();
        initFindChose();

        startGenBut.setEnabled(false);
        startFindBut.setEnabled(false);

        initBut.addActionListener(mal);
        startGenBut.addActionListener(mal);
        startFindBut.addActionListener(mal);

        add(new JLabel("Rows"));        add(rowSpinner);        add(new JLabel());
        add(new JLabel("Columns"));     add(colSpinner);        add(new JLabel());
        add(new JLabel("Cell Size"));   add(blockSizeSpinner);  add(new JLabel());
        add(new JLabel("Generator"));   add(genChose);          add(genCheck);
        add(new JLabel("Solver"));      add(findChose);         add(findCheck);
        add((new JLabel("Delay")));     add(delaySpinner);      add(new JLabel("seconds"));
        add(new JLabel("Image name"));  add(imageName);         add(new JLabel());
        add(initBut);                   add(startGenBut);       add(startFindBut);
    }

    public void setDisplayer(DisplayFrame disp) {
        df = disp;
    }

    public void initGenChose() {
        genChose = new JComboBox<>();
        for (String str : Options.getGenListOptions()) {
            genChose.addItem(str);
        }
    }

    public void initFindChose() {
        findChose = new JComboBox<>();
        for (String str : Options.getSolverListOptions()) {
            findChose.addItem(str);
        }
    }

    public void genComplete() {
        startGenBut.setEnabled(false);
        startFindBut.setEnabled(true);
    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            Object src = ae.getSource();
            if (src == initBut) {
                df.init();
                startGenBut.setEnabled(true);
                startFindBut.setEnabled(false);
            } else if (src == startGenBut) {
                // Start Maze Generation
                startGenBut.setEnabled(false);
                startFindBut.setEnabled(false);
            } else {
                // Start Maze Solving
                startFindBut.setEnabled(false);
            }
            df.doDraw();
        }
    }
}
