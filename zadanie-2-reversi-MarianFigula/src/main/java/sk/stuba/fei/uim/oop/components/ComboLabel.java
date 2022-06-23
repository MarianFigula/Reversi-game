package sk.stuba.fei.uim.oop.components;

import javax.swing.*;
import java.awt.*;

public class ComboLabel extends JLabel {
    public ComboLabel(GridBagConstraints gbc){
        super("Sizes:");
        Font font = new Font("Arial", Font.BOLD, 16);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,20,0,0);
        this.setFont(font);
    }
}
