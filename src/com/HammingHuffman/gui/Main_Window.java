package com.HammingHuffman.gui;

import model.Result_Type;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
    private JTextField first_panel_date_text_field;
    private JTextField first_panel_time_text_field;
    private JLabel first_panel_date_edit_label;
    private JLabel first_panel_hour_edit_label;


    public Main_Window() {

        /* GUI STATE, holds active state */
        gui_state = new GUI_State();

        /* LISTENERS FIRST PANEL*/

        first_panel_button_loadFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.first_panel_path_protect_compress = file_chooser_dialog();
                first_panel_text_field_path.setText(gui_state.first_panel_path_protect_compress);
            }
        });
        first_panel_protect_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.first_panel_protect = first_panel_protect_checkBox.isSelected();
            }
        });
        first_panel_button_accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Result_Type infoToShow = gui_state.first_panel_action();
                if(infoToShow.isError())
                    error_operation_Dialog();
                else
                    succesful_operationDialog(infoToShow);

            }
        });
        first_panel_module_size_comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (first_panel_module_size_comboBox.getSelectedIndex()) {
                    case 0:
                        gui_state.first_panel_module_size_first = 32;
                        break;
                    case 1:
                        gui_state.first_panel_module_size_first = 128;
                        break;
                    case 2:
                        gui_state.first_panel_module_size_first = 1024;
                        break;
                    case 3:
                        gui_state.first_panel_module_size_first = 4096;
                        break;
                    case 4:
                        gui_state.first_panel_module_size_first = 16384;
                        break;
                }
            }
        });
        first_panel_insert_error_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.first_panel_insertError = first_panel_insert_error_checkBox.isSelected();
            }
        });
        first_panel_compress_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.first_panel_compress = first_panel_compress_checkBox.isSelected();
            }
        });

        /* LISTENERS SECOND PANEL */

        second_panel_file_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.second_panel_path_unprotect_uncompress = file_chooser_dialog();
                second_panel_text_field_path.setText(gui_state.second_panel_path_unprotect_uncompress);
            }
        });
        second_panel_unprotect_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.second_panel_unprotect = second_panel_unprotect_checkBox.isSelected();
            }
        });
        second_panel_fix_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.second_panel_toCorrect = second_panel_fix_checkBox.isSelected();
            }
        });
        second_panel_unzip_checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui_state.second_panel_decompress = second_panel_unzip_checkBox.isSelected();
            }
        });
        second_panel_button_accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Result_Type infoToShow = gui_state.second_panel_action();
                if(infoToShow.isError())
                    error_operation_Dialog();
                else
                    succesful_operationDialog(infoToShow);
            }
        });

        first_panel_date_text_field.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                String[] aux_var = first_panel_date_text_field.getText().split("/");

                try {
                    gui_state.day = Integer.parseInt(aux_var[0]);
                    gui_state.month = Integer.parseInt(aux_var[1]);
                    gui_state.year = Integer.parseInt(aux_var[2]);
                } catch (Exception e) {
                /*    JOptionPane.showMessageDialog(null,
                            "Error: Ingresar fecha válida", "Mensaje de Error",
                            JOptionPane.ERROR_MESSAGE);*/
                }
            }

        });

        first_panel_time_text_field.getDocument().addDocumentListener((new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                String[] aux_var = first_panel_time_text_field.getText().split(":");

                try {
                    gui_state.hour = Integer.parseInt(aux_var[0]);
                    gui_state.minute = Integer.parseInt(aux_var[1]);
                    gui_state.segs = Integer.parseInt(aux_var[2]);
                } catch (Exception e) {
                /*    JOptionPane.showMessageDialog(null,
                            "Error: Ingresar fecha válida", "Mensaje de Error",
                            JOptionPane.ERROR_MESSAGE);*/
                }
            }

        }));

        /* LABELS USING GUI_STRINGS*/
        init_strings();

    }

    //Set GUI_Strings to UI
    public void init_strings(){


        /*FIRST PANEL */
        first_panel_title.setText(GUI_Strings.first_panel_title);

        first_panel_date_label.setText(GUI_Strings.date_section_title);
        first_panel_date_edit_label.setText(GUI_Strings.date_label);
        first_panel_hour_edit_label.setText(GUI_Strings.hour_label);

        first_panel_file_label.setText(GUI_Strings.load_file);
        first_panel_path_label.setText(GUI_Strings.path);

        first_panel_module_size_label.setText(GUI_Strings.module_size);
        first_panel_insert_error_label.setText(GUI_Strings.insert_errors);

        first_panel_compress_checkBox.setText(GUI_Strings.huffman);
        first_panel_protect_checkBox.setText(GUI_Strings.hamming);

        /*SECOND PANEL */

        second_panel_title_label.setText(GUI_Strings.second_panel_title);

        second_panel_file_label.setText(GUI_Strings.load_file);
        second_panel_path_label.setText(GUI_Strings.path);

        second_panel_fix_checkBox.setText(GUI_Strings.toCorrect);
        second_panel_unprotect_checkBox.setText(GUI_Strings.dehamming);
        second_panel_unzip_checkBox.setText(GUI_Strings.dehuffman);
    }

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
        frame.setSize(screenSize.width/2-100, screenSize.height/2 + 100);
        frame.setLocationRelativeTo(null);
    }

    //If Successful operation
    public void succesful_operationDialog(Result_Type fileSize){
        JOptionPane.showMessageDialog(null, "Operación Exitosa !\n *Tamaño Original = "+fileSize.getOriginalSize() + "\n *Tamaño Nuevo = "+fileSize.getNewSize(), "Resultado de la Operación", JOptionPane.INFORMATION_MESSAGE);
    }

    //Error in operation
    public void error_operation_Dialog(){
        JOptionPane.showMessageDialog(null, "Operación con Error !", "Mensaje de Error", JOptionPane.ERROR_MESSAGE);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
