package sk.stuba.fei.uim.oop.components;

import javax.swing.*;
import java.awt.*;

public class JLabelScore extends JLabel{
    public JLabelScore(GridBagConstraints gbc){
        super("2:2");
        Font font = new Font("Arial", Font.PLAIN, 14);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10,0,10,0);
        this.setFont(font);
    }
}
