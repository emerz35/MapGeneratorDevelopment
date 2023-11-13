package gui;

import generation.MapGenerator;
import generation.altitudegenerators.VoronoiPerlinAltitudeGenerator;
import generation.biomegenerators.WhittakerBiomeGenerator;
import generation.countrygenerators.DefaultCountryGenerator;
import generation.imagegenerators.AltitudeDependantImageGenerator;
import generation.landgenerators.SmoothedAltitudeDependantLandGenerator;
import generation.mapgenerators.DefaultMapGenerator;
import generation.rivergenerators.VoronoiRiverGenerator;
import generation.roadgenerators.DefaultRoadGenerator;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import javax.swing.JFileChooser;
import main.Main;
import static main.Main.M_HEIGHT;
import static main.Main.M_WIDTH;
import map.Map;


/**
 * The class that handles the painting of the map and GUI to the window
 * @author Tuesday
 */
public class Renderer implements Runnable{
    
    private final MapGenerator generator;
    private Map map;
    private final Main main;
    private static final long DELAY = 1000L/60L;
    private long now;
    
    public static volatile boolean loading;
    
    public GUIHandler handler;
    
    GUIButton generateBtn, saveBtn; 
    GUISlider landAltiSlider, landSlider, landMassSlider, riverNumSlider, countryNumSlider; 
    
    public static LoadingScreen loadingScreen = new LoadingScreen(M_WIDTH/4, 0);
    
    boolean running = true;
    
    /**
     * Constructor
     * @param m Instance of the main class
     */
    public Renderer(Main m){
        main = m;
        landAltiSlider = new GUISlider("Land Alitude",50,400,150,250,10,75,false);
        landSlider = new GUISlider("Land Mass Size",50,100,200,600,50,75,false);
        landMassSlider = new GUISlider("Land Mass Num",50, 200, 1, 5,1,75,true);
        riverNumSlider = new GUISlider("River Num",50,300,5,30,1,75,true);
        //countryNumSlider = new GUISlider(50,100,1,5,1,50,true);
        
        generator = new DefaultMapGenerator(new WhittakerBiomeGenerator(landAltiSlider), new VoronoiPerlinAltitudeGenerator(landSlider, landMassSlider),new DefaultCountryGenerator(countryNumSlider,landAltiSlider),new VoronoiRiverGenerator(landAltiSlider, riverNumSlider),new DefaultRoadGenerator(),new SmoothedAltitudeDependantLandGenerator(landAltiSlider),new AltitudeDependantImageGenerator(landAltiSlider));
        map = generator.generateMap();
        handler = new GUIHandler(map);
        handler.sliders.add(landSlider);
        handler.sliders.add(landAltiSlider);
        handler.sliders.add(landMassSlider);
        handler.sliders.add(riverNumSlider);
        //handler.sliders.add(countryNumSlider);
        generateBtn = new GUIButton("Genenerate",()-> new Thread(()->generateMap()).start(), 50,520,50,25);
        saveBtn = new GUIButton("Save", () -> {
            JFileChooser fc = new JFileChooser();
            try{
                if(fc.showSaveDialog(main) == JFileChooser.APPROVE_OPTION)map.saveToFile(fc.getSelectedFile());
            }catch(IOException e){
                e.printStackTrace(System.err);
            }
        }, 120,520,50,25);
        handler.btns.add(generateBtn);
        handler.btns.add(saveBtn);
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
        
        if(!loading)map.renderImage(g);
        else loadingScreen.paint(g);
        
        g.setColor(Color.GRAY);
        g.fill3DRect(0, 0, M_WIDTH/4, M_HEIGHT, true);
        landMassSlider.paint(g);
        landSlider.paint(g);
        landAltiSlider.paint(g);
        generateBtn.paint(g);
        riverNumSlider.paint(g);
        saveBtn.paint(g);
        //countryNumSlider.paint(g);
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
     * Generates a new map
     */
    private void generateMap(){
        //System.out.println(Thread.currentThread().getName());
        loading = true;
        Map.labels.clear();
        map = generator.generateMap();
        handler.changeMap(map);
        System.out.println("New Map Generated");
        loading = false;
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
