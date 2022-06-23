package sk.stuba.fei.uim.oop.components;

import javax.swing.*;
import java.awt.*;

public class JLabelVersus extends JLabel {
    public JLabelVersus(GridBagConstraints gbc){
        super("Player vs Computer");
        Font font = new Font("Arial", Font.PLAIN, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(15,0,2,0);
        this.setFont(font);
    }
}
