package com.HammingHuffman.gui;

//Class for object which holds the state of the GUI
public class GUI_State {

    /* First panel Variables */
    String path_protect_compress= "";
    boolean protect = false;
    int module_size_first = 32;
    boolean insertError = false;
    boolean compress = false;

    /* Second Panel Variables */
    String path_unprotect_uncompress= "";
    boolean unprotect = false;
    int module_size_second = 0;
    boolean toCorrect = false;
    boolean decompress = false;

    @Override
    public String toString() {
        return "GUI_State{" +
                "\n FIRST PANEL VARIABLES: " +
                "\n path_protect_compress='" + path_protect_compress + '\'' +
                ",\n protect=" + protect +
                ",\n module_size_first=" + module_size_first +
                ",\n insertError=" + insertError +
                ",\n compress=" + compress +
                "\n SECOND PANEL VARIABLES: " +
                ",\n path_unprotect_uncompress='" + path_unprotect_uncompress + '\'' +
                ",\n unprotect=" + unprotect +
                ",\n module_size_second=" + module_size_second +
                ",\n toCorrect=" + toCorrect +
                ",\n decompress=" + decompress +
                "\n }";
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
