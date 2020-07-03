package com.HammingHuffman.gui;

//Class for object which holds the state of the GUI
public class GUI_State {

    /* First panel Variables */
    String first_panel_path_protect_compress = "";
    boolean first_panel_protect = false;
    int first_panel_module_size_first = 32;
    boolean first_panel_insertError = false;
    boolean first_panel_compress = false;

    /* Second Panel Variables */
    String second_panel_path_unprotect_uncompress = "";
    boolean second_panel_unprotect = false;
    boolean second_panel_toCorrect = false;
    boolean second_panel_decompress = false;

    @Override
    public String toString() {
        return "GUI_State{" +
                "\n FIRST PANEL VARIABLES : " +
                "first_panel_path_protect_compress='" + first_panel_path_protect_compress + '\'' +
                ", first_panel_protect=" + first_panel_protect +
                ", first_panel_module_size_first=" + first_panel_module_size_first +
                ", first_panel_insertError=" + first_panel_insertError +
                ", first_panel_compress=" + first_panel_compress +
                "\n SECOND PANEL VARIABLES : " +
                ", second_panel_path_unprotect_uncompress='" + second_panel_path_unprotect_uncompress + '\'' +
                ", second_panel_unprotect=" + second_panel_unprotect +
                ", second_panel_toCorrect=" + second_panel_toCorrect +
                ", second_panel_decompress=" + second_panel_decompress +
                '}';
    }

    //TODO: CODE TO MODIFY INTERNALLY, ACCORDING TO STATE! - FIRST PANEL ACTION
    public void first_panel_action(){
        System.out.println(this);
    }

    //TODO: CODE TO MODIFY INTERNALLY, ACCORDING TO STATE! - SECOND PANEL ACTION
    public void second_panel_action(){
        System.out.println(this);
    }
}
