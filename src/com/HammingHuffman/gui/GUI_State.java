package com.HammingHuffman.gui;

import model.Result_Type;

//Class for object which holds the state of the GUI
public class GUI_State {

    /* First panel Variables */
    String first_panel_path_protect_compress = "";
    boolean first_panel_protect = false;
    int first_panel_module_size_first = 32;
    boolean first_panel_insertError = false;
    boolean first_panel_compress = false;
    int day = 0;
    int month = 0;
    int year = 0;
    int hour = 0;
    int minute = 0;
    int segs = 0;

    /* Second Panel Variables */
    String second_panel_path_unprotect_uncompress = "";
    boolean second_panel_unprotect = false;
    boolean second_panel_toCorrect = false;
    boolean second_panel_decompress = false;

    //Good for debugging!
    @Override
    public String toString() {
        return "GUI_State{" +
                "\n FIRST PANEL VARIABLES : " +
                "\nfirst_panel_path_protect_compress='" + first_panel_path_protect_compress + '\'' +
                ", \nfirst_panel_protect=" + first_panel_protect +
                ", \nfirst_panel_module_size_first=" + first_panel_module_size_first +
                ", \nfirst_panel_insertError=" + first_panel_insertError +
                ", \nfirst_panel_compress=" + first_panel_compress +
                ", \nday = " +day + " ,month = " + month +  ", year= " + year +
                ", \nhour = "+ hour + ", minutes = " +minute + ", segs = " + segs +
                "\n SECOND PANEL VARIABLES : " +
                ", \nsecond_panel_path_unprotect_uncompress='" + second_panel_path_unprotect_uncompress + '\'' +
                ", \nsecond_panel_unprotect=" + second_panel_unprotect +
                ", \nsecond_panel_toCorrect=" + second_panel_toCorrect +
                ", \nsecond_panel_decompress=" + second_panel_decompress +
                '}';
    }

    //TODO: CODE TO MODIFY INTERNALLY, ACCORDING TO STATE! - FIRST PANEL ACTION
    public Result_Type first_panel_action(){
        System.out.println(this);


        //Example of return
        return new Result_Type(300.0, 100.0, false);
    }

    //TODO: CODE TO MODIFY INTERNALLY, ACCORDING TO STATE! - SECOND PANEL ACTION
    public Result_Type second_panel_action(){
        System.out.println(this);

        //Example of return
        return new Result_Type(300.0, 100.0, true);
    }
}
