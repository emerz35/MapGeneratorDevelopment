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
    
    private final Map map;
    
    private int prevX,prevY;
    public GUIHandler(Map m){
        map = m;
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        btns.stream().filter(x->x.inBounds(e.getX(), e.getY())).forEach(x->x.action());
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        sliders.forEach(x-> x.sliding = x.inBounds(e.getX(), e.getY()));
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        sliders.forEach(x-> x.sliding = false);
    }
    
    @Override 
    public void mouseWheelMoved(MouseWheelEvent e){
       
        map.zoom(-e.getWheelRotation());
        map.translateX((int)((double)(map.topX-e.getX())*(map.zoom/(map.zoom+e.getWheelRotation())-1)));
        map.translateY((int)((double)(map.topY-e.getY())*(map.zoom/(map.zoom+e.getWheelRotation())-1)));
        
    }
    
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
    
    @Override
    public void mouseMoved(MouseEvent e){
        prevX = e.getX();
        prevY = e.getY();
    }
    
}
