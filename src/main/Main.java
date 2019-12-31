package main;

import gui.Renderer;
import java.awt.Canvas;

/**
 * The main class. This will contain the main method that is initially run. It will also start the main loop
 * @author Charlie
 * 
 */

public class Main extends Canvas{
    
    public final static int M_WIDTH = 800, M_HEIGHT = (M_WIDTH/12) *9;
   
    public Thread renderThread;
    
    /**
     * Constructor for main
     */
    public Main() {}
    
    /**
     * Starts the main loop by creating an instance of Renderer class, and assigning a thread to it
     */
    public void start() {
        requestFocus();
        createBufferStrategy(4);
        Renderer renderer = new Renderer(this);
        this.addMouseListener(renderer.handler);
        this.addMouseMotionListener(renderer.handler);
        this.addMouseWheelListener(renderer.handler);
        renderThread = new Thread(renderer);
        renderThread.setDaemon(true);
        renderThread.start();
    }
    
    /**
     * The main method
     * @param args 
     */
    public static void main(String... args){
        new Window("Map Generator",M_WIDTH,M_HEIGHT,new Main());
    }
}
