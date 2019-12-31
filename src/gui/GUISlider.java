package gui;

import java.awt.Color;
import java.awt.Graphics2D;

public class GUISlider {

    private final int absX,absY,length,sHeight = 12,sWidth = 6, min,max,lineSpacing;

    private final boolean sticky;
    public boolean sliding = false;

    private int sX;
    
    /**
     * 
     * @param x
     * @param y
     * @param min
     * @param max
     * @param spacing
     * @param length
     * @param sticky 
     */
    public GUISlider(int x, int y, int min, int max, int spacing, int length, boolean sticky) {
        absX = x;
        absY = y;
        this.min = min;
        this.max = max;
        lineSpacing = spacing;
        this.length = length;
        this.sticky = sticky;
        sX = x;
    }
    /**
     * 
     * @param g 
     */
    public void paint(Graphics2D g) {
        g.setColor(Color.black);
        
        //Draws horizontal line
        g.drawLine(absX,absY,absX+length,absY);
        
        //Draws lines at both ends
        g.drawLine(absX,absY+sHeight/2,absX,absY-sHeight/2);
        g.drawLine(absX+length,absY+sHeight/2,absX+length,absY-sHeight/2);
        
        int x = 0;
        //draws lines at specific intervals along the slider
        for(int i = 1; i<max-min;i+=lineSpacing){
            x = absX+(int)( (double)(i)/(double)(max-min) * (double)length);
            g.drawLine(x,absY+sHeight/4,x,absY-sHeight/4);
        }
        
        g.setColor(Color.darkGray);
        g.fill3DRect((int)sX-sWidth/2, absY-sHeight/2, sWidth, sHeight, true);
    }
    
    /**
     * 
     * @param mx
     * @param my
     * @return 
     */
    public boolean inBounds(int mx, int my) {
        return mx>=sX&&mx<=sX+sWidth&&my>=absY+sHeight/2&&my<=absY-sHeight/2;
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
    }

    /**
     * Gets the value the slider is pointing to
     * @return The value the slider is pointing to
     */
    public double getNum() {
        if(sticky) return min +(int)( (double)(max - min)*(double)(sX-absX)/(double)length);
        return (double)min + (double)(max - min)*(double)(sX-absX)/(double)length; 
    }
}
