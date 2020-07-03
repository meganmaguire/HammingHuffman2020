package com.HammingHuffman.gui;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Main_Window {

    private GUI_State gui_state;
    private JPanel rootPanel; //Root where is all mounted on
    private JButton first_panel_button_loadFile;
    private JTextField first_panel_text_field_path;
    private JCheckBox first_panel_protect_checkBox;
    private JComboBox first_panel_module_size_comboBox;
    private JCheckBox first_panel_insert_error_checkBox;
    private JCheckBox first_panel_compress_checkBox;
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


    public Main_Window() {

        /* GUI STATE, holds active state */
        gui_state = new GUI_State();

        /* LISTENERS FIRST PANEL*/

        first_panel_button_loadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.path_protect_compress = file_chooser_dialog();
                first_panel_text_field_path.setText(gui_state.path_protect_compress);
            }
        });
        first_panel_protect_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.protect = first_panel_protect_checkBox.isSelected();
            }
        });
        first_panel_button_accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.first_panel_action();
            }
        });
        first_panel_module_size_comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (first_panel_module_size_comboBox.getSelectedIndex()){
                    case 0:
                        gui_state.module_size_first = 32;
                        break;
                    case 1:
                        gui_state.module_size_first = 128;
                        break;
                    case 2:
                        gui_state.module_size_first = 1024;
                        break;
                    case 3:
                        gui_state.module_size_first = 4096;
                        break;
                    case 4:
                        gui_state.module_size_first = 16384;
                        break;
                }
            }
        });
        first_panel_insert_error_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.insertError = first_panel_insert_error_checkBox.isSelected();
            }
        });
        first_panel_compress_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.compress = first_panel_compress_checkBox.isSelected();
            }
        });

        /* LISTENERS SECOND PANEL */

        second_panel_file_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.path_unprotect_uncompress = file_chooser_dialog();
                second_panel_text_field_path.setText(gui_state.path_unprotect_uncompress);
            }
        });
        second_panel_unprotect_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.protect = second_panel_unprotect_checkBox.isSelected();
            }
        });
        second_panel_fix_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.toCorrect = second_panel_fix_checkBox.isSelected();
            }
        });
        second_panel_unzip_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.decompress = second_panel_unzip_checkBox.isSelected();
            }
        });
        second_panel_button_accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.second_panel_action();
            }
        });
    }


    /* SCREEN UTILITIES METHODS */

    //Returns absolute path to the file selected
    public String file_chooser_dialog(){
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
