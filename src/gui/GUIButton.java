package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class GUIButton {

    private final ButtonAction action;

    private final int x,y,width,height;
    
    private final String name;
    private final static Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 9);

    public GUIButton(String name, ButtonAction a, int x, int y, int width, int height) {
        action = a;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Paints the button to the graphics
     * @param g The graphics used to paint the button
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.gray);
        g.fill3DRect(x, y, width, height, true);
        
        g.setColor(Color.BLACK);
        
        g.setFont(BUTTON_FONT);
        
        FontMetrics gFM = g.getFontMetrics();
        
        g.drawString(name, x+(width-gFM.stringWidth(name))/2, y+ (height+gFM.getHeight())/2);
    }
    
    /**
     * Calls the action on ButtonAction interface
     */
    public void action() {
        action.action();
    }
    
    /**
     * Checks if the mouse has clicked within the bounds of the button
     * @param mx x coordinate of the mouse
     * @param my y coordinate of the mouse
     * @return Whether the mouse is in bounds or not
     */
    public boolean inBounds(int mx, int my) {
        return mx >= x && mx <= x+width && my >= y && my <= y+height;
    }
}
