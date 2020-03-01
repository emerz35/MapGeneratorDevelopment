package map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Label {

    public String str;

    private int x;

    private int y;
    
    public Label(String str,int x, int y){
        this.str = str;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics2D g) {
        g.setFont(new Font("Ariel", Font.PLAIN,10));
        g.setColor(Color.RED);
        g.drawString(str, x, y);
    }
}
