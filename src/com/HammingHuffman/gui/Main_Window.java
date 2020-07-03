package com.HammingHuffman.gui;

import javax.swing.*;
import java.awt.*;

public class Main_Window {
    private JPanel rootPanel;
    private JLabel title;

    public JPanel getRootPanel(){
        return this.rootPanel;
    };


    /* SCREEN UTILITIES METHODS */

    //Center the JFrame on the screen
    public void setCentered(JFrame frame){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width/2, screenSize.height/2);
        frame.setLocationRelativeTo(null);
    }
}
