package sk.stuba.fei.uim.oop.components;

import javax.swing.*;
import java.awt.*;

public class JLabelTurn extends JLabel {
    public JLabelTurn(GridBagConstraints gbc){
        super();
        Font font = new Font("Arial", Font.BOLD, 12);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10,0,10,0);
        this.setFont(font);
    }
}
