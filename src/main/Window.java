package main;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window {

    private final JFrame frame;

    public Window(String name, int width, int height, Main m){
        //Initialises the frame and sets the frame's properties
        frame = new JFrame(name);
        frame.setSize(new Dimension(width,height));
        frame.setMinimumSize(new Dimension(width,height));
        frame.setMaximumSize(new Dimension(width,height));
        frame.setPreferredSize(new Dimension(width,height));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        //Adds main to the frame and runs main
        frame.add(m);
        m.start();
    }
}
