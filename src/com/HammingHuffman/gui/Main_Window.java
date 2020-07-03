package com.HammingHuffman.gui;

import javax.swing.*;
import java.awt.*;

public class Main_Window {
    private JPanel rootPanel; //Root where is all mounted on
    private JButton first_panel_button_loadFile;
    private JTextField first_panel_text_field_path;
    private JCheckBox first_panel_protect_checkBox;
    private JComboBox first_panel_module_size_comboBox;
    private JCheckBox first_panel_insert_error_checkBox;
    private JCheckBox first_panel_compress_label;
    private JButton first_panel_button_accept;
    private JButton second_panel_file_button;
    private JCheckBox second_panel_unprotect_checkBox;
    private JCheckBox second_panel_unzip_checkBox;
    private JCheckBox second_panel_fix_checkBox;
    private JTextField second_panel_text_field_path;
    private JButton second_panel_button_accept;
    private JPanel second_panel;
    private JPanel first_panel;
    private JLabel first_panel_title;
    private JLabel first_panel_date_label;
    private JLabel first_panel_file_label;
    private JLabel first_panel_path_label;
    private JLabel first_panel_module_size_label;
    private JLabel first_panel_insert_error_label;
    private JLabel second_panel_title_label;
    private JLabel second_panel_file_label;
    private JLabel second_panel_path_label;


    /* SCREEN UTILITIES METHODS */
    //Return all the content
    public JPanel getRootPanel(){
        return this.rootPanel;
    };

    //Center the JFrame on the screen
    public void setCentered(JFrame frame){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width/2, screenSize.height/2);
        frame.setLocationRelativeTo(null);
    }
}
