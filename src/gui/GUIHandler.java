package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.LinkedList;
import map.Map;

/**
 * Handles mouse input
 * @author Charlie
 */
public class GUIHandler extends MouseAdapter{
    
    public final LinkedList<GUIButton> btns = new LinkedList<>();
    public final LinkedList<GUISlider> sliders = new LinkedList<>();
    
    private Map map;
    
    private int prevX,prevY;
    public GUIHandler(Map m){
        map = m;
    }
    
    /**
     * Sets the current map being displayed to allow it to be zoomed and panned
     * @param m 
     */
    public void changeMap(Map m){
        map = m;
    }
    
    /**
     * Mouse click event - calls the action for each button that has been clicked
     * @param e the mouse event corresponding to the click
     */
    @Override
    public void mouseClicked(MouseEvent e){
        btns.stream().filter(x->x.inBounds(e.getX(), e.getY())).forEach(x->x.action());
    }
    
    /**
     * Mouse pressed event - sets each slider that the mouse is over to sliding when the mouse is pressed down
     * @param e The mouse event corresponding to the mouse press
     */
    @Override
    public void mousePressed(MouseEvent e){
        sliders.forEach(x-> x.sliding = x.inBounds(e.getX(), e.getY()));
    }
    
    /**
     * Mouse released event - sets sliding to false for each slider when the mouse is released
     * @param e The mouse event corresponding to the mouse release
     */
    @Override
    public void mouseReleased(MouseEvent e){
        sliders.forEach(x-> x.sliding = false);
    }
    
    /**
     * Mouse wheel moved event - handles zooming of the map
     * @param e  The mouse event corresponding to the mouse wheel moving
     */
    @Override 
    public void mouseWheelMoved(MouseWheelEvent e){
        map.zoom(-e.getWheelRotation(),e.getX(),e.getY());
    }
    
    /**
     * Mouse dragged event. - Updates the x coordinate of sliders being dragged. If no sliders are being dragged, it pans the map
     * @param e The mouse event corresponding to the mouse dragging
     */
    @Override
    public void mouseDragged(MouseEvent e){
        sliders.stream().filter(x->x.sliding).forEach(x->x.updateX(e.getX()));
        if(!sliders.stream().anyMatch(x->x.sliding)){
            map.translateX(e.getX()-prevX);
            map.translateY(e.getY()-prevY);
            prevX = e.getX();
            prevY = e.getY();
        }
    }
    
    /**
     * Mouse moved event -  Updates the position of the mouse. This position is used to get directions of panning
     * @param e The mouse event corresponding to the mouse moving
     */
    @Override
    public void mouseMoved(MouseEvent e){
        prevX = e.getX();
        prevY = e.getY();
    }
    
}
