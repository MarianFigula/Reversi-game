package sk.stuba.fei.uim.oop.components;

import javax.swing.*;
import java.awt.*;

public class JLabelSize extends JLabel {
    public JLabelSize(int areaSize, GridBagConstraints gbc){
        super();
        Font font = new Font("Arial", Font.PLAIN, 12);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10,0,10,0);
        this.setFont(font);
        this.setText("Size: " + areaSize + "x" + areaSize + " ");
    }
}
