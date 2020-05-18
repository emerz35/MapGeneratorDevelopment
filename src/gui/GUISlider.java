package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

public class GUISlider {

    private final int absX,absY,length,sHeight = 24,sWidth = sHeight/2, min,max,lineSpacing, labelOffset = 10;

    private final boolean sticky;
    public boolean sliding = false;

    private int sX;
    private double num;
    private String name;
    
    private DecimalFormat rounding = new DecimalFormat("#.##");
    
    /**
     * Constructor for the slider
     * @param n name of the slider to display
     * @param x x coordinate of the left most point of the slider
     * @param y y coordinate of the middle of the slider
     * @param min Value corresponding to the left most point of the slider
     * @param max Value corresponding to the right most point of the slider
     * @param spacing The gap in terms of values between adjacent bars 
     * @param length The absolute length of the slider in terms of pixels
     * @param sticky Whether the slider sticks to integer values or not - sticky should be false for large differences between min and max
     */
    public GUISlider(String n, int x, int y, int min, int max, int spacing, int length, boolean sticky) {
        name = n;
        absX = x;
        absY = y;
        this.min = min;
        num = min;
        this.max = max;
        lineSpacing = spacing;
        this.length = length;
        this.sticky = sticky;
        sX = x;
    }
    
    /**
     * Paints the slider to the graphics
     * @param g The graphics used to paint slider
     */
    public void paint(Graphics2D g) {
        
        g.setColor(Color.black);
        
        //Draws horizontal line
        g.drawLine(absX,absY,absX+length,absY);
        
        //Draws lines at both ends
        g.drawLine(absX,absY+sHeight/2,absX,absY-sHeight/2);
        g.drawLine(absX+length,absY+sHeight/2,absX+length,absY-sHeight/2);
        
        g.setFont(new Font("Arial", Font.PLAIN,12));
        
        int fontHeight = g.getFontMetrics().getHeight();
        
        g.drawString(name,absX, absY - fontHeight);
        
        g.drawString(rounding.format(num), absX+length + labelOffset, absY+fontHeight/2);
        
        
        int x = 0;
        //draws lines at specific intervals along the slider
        for(int i = lineSpacing; i<max-min;i+=lineSpacing){
            x = absX+(int)((double)(i)/(double)(max-min) * (double)length);
            g.drawLine(x,absY+sHeight/4,x,absY-sHeight/4);
        }
        
        g.setColor(Color.darkGray);
        g.fill3DRect((int)sX-sWidth/2, absY-sHeight/2, sWidth, sHeight, true);
    }
    
    /**
     * Checks whether the mouse in within of the slider handle
     * @param mx x coordinate of the mouse
     * @param my y coordinate of the mouse
     * @return whether the mouse in within of the slider handle
     */
    public boolean inBounds(int mx, int my) {
        return mx >= sX && mx <= sX+sWidth && my >= absY - sHeight/2 && my <= absY+sHeight;
    }
    
    /**
     * Changes the x coordinate of the slider (in testing, see if should change to a change in x)
     * @param x The value of x to change the slider to
     */
    public void updateX(int x) {
        if(x<=absX)sX = absX;
        else if(x>= absX+length) sX=absX+length;
        else if(!sticky)sX = x;
        else sX = absX + (int)((double)(x-absX)*(double)(max-min)/(double)length)*(int)((double)length/(double)(max-min));
        num = calcNum();
    }

    /**
     * Gets the value the slider is pointing to
     * @return The value the slider is pointing to
     */
    public synchronized double getNum() {
        return num;
    }
    
    private double calcNum(){
        if(sticky) return min +Math.rint((double)(max - min)*(double)(sX-absX)/(double)length);
        return (double)min + (double)(max - min)*(double)(sX-absX)/(double)length; 
    }
}
