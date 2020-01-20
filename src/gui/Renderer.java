package gui;

import generation.MapGenerator;
import generation.altitudegenerators.VoronoiPerlinAltitudeGenerator;
import generation.imagegenerators.BiomeDependantImageGenerator;
import generation.landgenerators.SmoothedAltitudeDependantLandGenerator;
import generation.mapgenerators.DefaultMapGenerator;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import main.Main;
import map.Map;


/**
 * The class that handles the painting of the map and GUI to the window
 * @author Charlie
 */
public class Renderer implements Runnable{
    
    private final MapGenerator generator;
    private Map map;
    private final Main main;
    private static final long DELAY = 1000L/60L;
    private long now;
    
    public GUIHandler handler;
    
    GUIButton generateBtn; 
    GUISlider testSlider; 
    
    boolean running = true;
    
    /**
     * Constructor
     * @param m Instance of the main class
     */
    public Renderer(Main m){
        main = m;
        testSlider = new GUISlider(50,500,100,500,50,50,false);
        generator = new DefaultMapGenerator(null, new VoronoiPerlinAltitudeGenerator(),null,null,null,new SmoothedAltitudeDependantLandGenerator(testSlider),new BiomeDependantImageGenerator());
        map = generator.generateMap();
        handler = new GUIHandler(map);
        
        handler.sliders.add(testSlider);
        generateBtn = new GUIButton(()-> {generateMap();System.out.println("New Map Generated");}, 50,530,50,25);
        handler.btns.add(generateBtn);
    }
    
    /**
     * Paints all the elements of the gui to the window
     */
    public void paint() {
        //Creates the graphics to paint to
        BufferStrategy bs = main.getBufferStrategy();
        Graphics2D g = (Graphics2D)bs.getDrawGraphics();
        //Draws the relevant features to Graphics2d
        g.fillRect(0, 0, Main.M_WIDTH, Main.M_HEIGHT);
        map.renderImage(g);
        g.setColor(Color.GRAY);
        g.fill3DRect(0, 0, Main.M_WIDTH/4, Main.M_HEIGHT, true);
        
        testSlider.paint(g);
        generateBtn.paint(g);
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
    
    private void generateMap(){
        map = generator.generateMap();
        handler.changeMap(map);
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
