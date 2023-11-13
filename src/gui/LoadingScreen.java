package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import static main.Main.M_HEIGHT;
import static main.Main.M_WIDTH;

/**
 *
 * @author Tuesday Hands
 */
public class LoadingScreen {
    
    private static final Font MESSAGE_FONT = new Font("Arial", Font.PLAIN, 25);
    public volatile String message = "";
    private final int x,y, angleSpeed = 5, arcDiameter = M_WIDTH/8, thickness = 40;
    
    private int startAngle = 90, angleSize = 0; 
    private boolean waning = false;
    
    public LoadingScreen(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void paint(Graphics2D g){
        g.setColor(Color.BLACK);
        
        g.fillRect(0, 0, M_WIDTH, M_HEIGHT);
        
        if(waning){
            angleSize-=angleSpeed;
            startAngle+=angleSpeed;
            startAngle%=360;
            if(angleSize<=0)waning = false;
        }
        else{
            angleSize+=angleSpeed;
            if(angleSize>=360)waning = true;
        }
        
        g.setColor(Color.WHITE);
        g.setFont(MESSAGE_FONT);
        
        g.fillArc((x+M_WIDTH-arcDiameter)/2, (y+M_HEIGHT-arcDiameter)/2, arcDiameter, arcDiameter, startAngle, angleSize);
  
        
        FontMetrics fm = g.getFontMetrics();
        
        g.drawString(message, (x+M_WIDTH-fm.stringWidth(message))/2 , 3*(M_HEIGHT+y-fm.getHeight())/4);
        
        g.setColor(Color.BLACK);
        g.fillOval((x+M_WIDTH-arcDiameter+thickness)/2, (y+M_HEIGHT-arcDiameter+thickness)/2, arcDiameter-thickness, arcDiameter-thickness);
        
    }
    
    
}
