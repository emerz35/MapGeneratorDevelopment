package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.LinkedList;

/**
 * Handles mouse input
 * @author Charlie
 */
public class GUIHandler extends MouseAdapter{
    
    public LinkedList<GUIButton> btns = new LinkedList<>();
    public LinkedList<GUISlider> sliders = new LinkedList<>();
    
    @Override
    public void mouseClicked(MouseEvent e){
        btns.stream().filter(x->x.inBounds(e.getX(), e.getY())).forEach(x->x.action());
    }
    
    @Override
    public void mousePressed(MouseEvent e){
    
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
    
    }
    
    @Override 
    public void mouseWheelMoved(MouseWheelEvent e){
    
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
    
    }
    
    @Override
    public void mouseMoved(MouseEvent e){
        
    }
    
}
