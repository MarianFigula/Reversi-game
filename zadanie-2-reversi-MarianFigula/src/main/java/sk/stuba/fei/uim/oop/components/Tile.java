package sk.stuba.fei.uim.oop.components;

import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

@Getter @Setter
public class Tile extends JPanel {
    private static final int SIZE = 80;
    private static final Color BORDER_COLOR = new Color(0,0,0,150);
    private Color stoneColor;
    private int centerX;
    private int centerY;
    private int radius;
    private boolean clickable;

    public Tile(Color stoneColor, boolean clickable){
        super();
        this.stoneColor = stoneColor;
        this.clickable = clickable;
        Border blackBorder = BorderFactory.createLineBorder(BORDER_COLOR);
        this.setSize(SIZE,SIZE);
        this.setBorder(blackBorder);
    }

    private void calculatePosition(){
        setCenterX(getWidth()/2);
        setCenterY(getHeight()/2);
        setRadius(getWidth()/4);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (getStoneColor() != null){
            g.setColor(getStoneColor());
            calculatePosition();
            g.fillOval(getCenterX()-getRadius(), getCenterY()-getRadius(), getRadius()*2, getRadius()*2);
        }
        repaint();
        revalidate();
    }
}
