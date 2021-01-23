package main;

import javax.swing.JFrame;

public class DisplayFrame extends JFrame {
    Chooser ch;

    public DisplayFrame(Chooser ch) {
        this.ch = ch;
        setSize(700, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void init() {
        
    }
}
