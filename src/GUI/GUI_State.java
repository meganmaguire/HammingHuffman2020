package GUI;

import Huffman.*;
import model.Result_Type;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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

    /* Variables estáticas */
    private static Map<Character,Integer> tablaFreq = new HashMap<>();
    private static Map<Character,String> tablaCod = new HashMap<>();
    private static Map<String,Character> tablaDecod = new HashMap<>();


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

        String[] nameTrim;
        String path;
        double inSize = 0;
        double outSize = 0;

        // Eligió comprimir
        if(first_panel_compress){

            // Genero el nuevo path con el mismo nombre pero nueva extensión
            nameTrim = first_panel_path_protect_compress.split("\\.");
            nameTrim[(nameTrim.length)-1] = "HUF";
            path = String.join(".",nameTrim);

            char[] module = new char[200];
            ModuloCod codModule;
            int chars = 0;

            try {
                // Abro el archivo original
                File fileR = new File(first_panel_path_protect_compress);
                FileInputStream fr1 = new FileInputStream(fileR);
                BufferedReader br = new BufferedReader(new InputStreamReader(fr1, StandardCharsets.ISO_8859_1));
                inSize = fileR.length();

                // Creo el nuevo archivo para escribir
                File fileW = new File(path);
                fileW.createNewFile();
                FileOutputStream fw2 = new FileOutputStream(fileW);
                BufferedOutputStream bw = new BufferedOutputStream(fw2);

                // Leo un módulo
                module = LecturaArchivo.leerChars(br);
                chars = LecturaArchivo.getChars();

                do {
                    // Generación de la tabla de frecuencia
                    Compresion.tablaFrecuencia(module, chars);

                    // Leo un módulo
                    module = LecturaArchivo.leerChars(br);
                    chars = LecturaArchivo.getChars();

                }
                while (chars != -1);

                // Genero codificación
                Compresion.generarCodificacion();

                // Escribo codificación en el txt
                Compresion.escribirTabla(bw);

                // Cierro y vuelvo abrir archivo para leer desde el comienzo
                br.close();
                fr1 = new FileInputStream(first_panel_path_protect_compress);
                br = new BufferedReader(new InputStreamReader(fr1, StandardCharsets.ISO_8859_1));

                // Leo un módulo
                module = LecturaArchivo.leerChars(br);
                chars = LecturaArchivo.getChars();

                do {
                    // Codifico
                    codModule = Compresion.codificar(module, chars);

                    // Escribo
                    EscrituraArchivo.escribirBytes(bw,codModule);

                    // Leo un módulo
                    module = LecturaArchivo.leerChars(br);
                    chars = LecturaArchivo.getChars();

                }
                while (chars != -1);

                outSize = fileW.length();
                bw.close();
                br.close();

            } catch (IOException e) {
                System.out.println("Error en archivo.");
            }


        }


        //Example of return
        return new Result_Type(inSize, outSize, false);
    }

    //TODO: CODE TO MODIFY INTERNALLY, ACCORDING TO STATE! - SECOND PANEL ACTION
    public Result_Type second_panel_action(){
        System.out.println(this);

        String[] nameTrim;
        String path;
        double inSize = 0;
        double outSize = 0;

        if(second_panel_decompress){

            // Genero el nuevo path con el mismo nombre pero nueva extensión
            nameTrim = second_panel_path_unprotect_uncompress.split("\\.");
            nameTrim[(nameTrim.length)-1] = "DHU";
            path = String.join(".",nameTrim);

            try {

                ModuloCod moduloCod;
                String module;

                // Abro el archivo original
                File fileR = new File(second_panel_path_unprotect_uncompress);
                FileInputStream fr = new FileInputStream(fileR);
                BufferedInputStream br = new BufferedInputStream(fr);
                inSize = fileR.length();

                // Creo el nuevo archivo para escribir
                File fileW = new File(path);
                fileW.createNewFile();
                FileOutputStream fw = new FileOutputStream(fileW);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fw, StandardCharsets.ISO_8859_1));

                // Leo la tabla
                ModuloCod tablaMod = LecturaArchivo.leerTabla(br);

                // Armo la tabla
                Descompresion.generarTabla(tablaMod);

                // Leo un módulo
                moduloCod = LecturaArchivo.leerBytes(br);

                do{
                    // Decodifico
                    module = Descompresion.decodificar(moduloCod);

                    //Escribo
                    EscrituraArchivo.escribirChar(bw,module);

                    // Leo un módulo
                    moduloCod = LecturaArchivo.leerBytes(br);
                }
                while(moduloCod.getChars() != -1);

                outSize = fileW.length();
                br.close();
                bw.close();

            }
            catch (IOException e){
                System.out.println("Error en archivo.");
            }

        }


        //Example of return
        return new Result_Type(inSize, outSize, false);
    }
}
