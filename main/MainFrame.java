package main;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        Chooser ch = new Chooser();
        DisplayFrame df = new DisplayFrame(ch);
        ch.setDisplayer(df);
        
        JScrollPane jsp = new JScrollPane(df);
        jsp.setBorder(new LineBorder(Color.BLACK, 2));
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        
        setLayout(gbl);
        
        gbc.gridx = 0;
        gbc.weightx = 0.001;

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        JLabel lab = new JLabel("Display on the panel", JLabel.CENTER);
        lab.setFont(new Font("Calibri", Font.BOLD, 20));
        lab.setBorder(new LineBorder(Color.BLACK));
        add(lab, gbc);
        
        gbc.gridy = 1;
        gbc.weighty = 0.3;
        add(ch, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        gbc.weighty = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(jsp, gbc);
        
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
