package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Main;
import map.Map;


/**
 * The class that handles the painting of the map and GUI to the window
 * @author Charlie
 */
public class Renderer implements Runnable{
    
    private Map map;
    private Main main;
    private static final long DELAY = 1000L/60L;
    private long now;
    
    GUIButton testBtn; 
    GUISlider testSlider; 
    
    boolean running = true;
    
    /**
     * Constructor
     * @param m Instance of the main class
     */
    public Renderer(Main m){
        main = m;
        try{
        BufferedImage image = ImageIO.read(new File("images_test\\testMap.png"));
        map = new Map(image);
        }catch(IOException e){
        }
        
        testSlider = new GUISlider(1000,1500,0,100,10,200,true);
        testBtn = new GUIButton(()->System.out.println("Button works. Slider has value " + testSlider.getNum()), 1000,1000,200,50);
    }
    
    /**
     * Paints all the elements of the gui to the window
     */
    public void paint() {
        //Creates the graphics to paint to
        BufferStrategy bs = main.getBufferStrategy();
        Graphics2D g = (Graphics2D)bs.getDrawGraphics();
        //Draws the relevant features to Graphics2d
        map.renderImage(g);
        testSlider.paint(g);
        testBtn.paint(g);
        //Displays the graphics to the window
        g.dispose();
        bs.show();
    }
    
    /**
     * The method that handles the main loop
     */
    @Override
    public void run() {
        long n,timer = System.currentTimeMillis();
        int frames = 0,f;
        while(running){
            n = System.currentTimeMillis();
            while(n-now>DELAY){
                f = (int)((n-now)/DELAY);
                paint();
                now = n;
                frames++;
            }

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    
    /**
     * Stops the thread
     */
    public synchronized void stop(){
        try {
            main.renderThread.join();
            running = false;
        } catch (InterruptedException ex) {
            System.err.println("Error in stop()");
        }
    }
}
