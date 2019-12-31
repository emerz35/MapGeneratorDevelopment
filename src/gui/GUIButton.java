package gui;


import java.awt.Color;
import java.awt.Graphics2D;

public class GUIButton {

    private final ButtonAction action;

    private final int x,y,width,height;

    public GUIButton(ButtonAction a,int x, int y, int width, int height) {
        action = a;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.gray);
        g.fill3DRect(x, y, width, height, true);
    }

    public void action() {
        action.action();
    }

    public boolean inBounds(int mx, int my) {
        return mx >= x && mx <= x+width && my >= y && my <= y+height;
    }
}
