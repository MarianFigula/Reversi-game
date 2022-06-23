package sk.stuba.fei.uim.oop.components;

import javax.swing.*;
import java.awt.*;

public class ResetButton extends JButton{
    public ResetButton(GridBagConstraints gbc){
        super("Reset");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(80,10,0,10);
        gbc.ipadx = 80;
        gbc.ipady = 10;
        this.setFocusable(false);
    }
}
