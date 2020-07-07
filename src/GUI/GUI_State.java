package GUI;

import model.DateBlock;
import model.ResultType;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    SpinnerDateModel first_panel_date_time_model = new SpinnerDateModel();
    int day = 0;
    int month = 0;
    int year = 0;
    int hour = 0;
    int minute = 0;

    /* Second Panel Variables */
    String second_panel_path_unprotect_uncompress = "";
    boolean second_panel_unprotect = false;
    boolean second_panel_toCorrect = false;
    boolean second_panel_decompress = false;

    /* Date formats */
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
    SimpleDateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

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
                ", \nhour = "+ hour + ", minutes = " +minute + ", segs = " +
                "\n SECOND PANEL VARIABLES : " +
                ", \nsecond_panel_path_unprotect_uncompress='" + second_panel_path_unprotect_uncompress + '\'' +
                ", \nsecond_panel_unprotect=" + second_panel_unprotect +
                ", \nsecond_panel_toCorrect=" + second_panel_toCorrect +
                ", \nsecond_panel_decompress=" + second_panel_decompress +
                '}';
    }

    //TODO: CODE TO MODIFY INTERNALLY, ACCORDING TO STATE! - FIRST PANEL ACTION
    public ResultType first_panel_action(){

        String[] nameTrim;
        double[] sizes = new double[2];

        Date date = first_panel_date_time_model.getDate();

        String stringDate = dateFormat.format(date);
        String stringTime = timeFormat.format(date);


        // Eligió comprimir
        if(first_panel_compress){

            String srcPath = first_panel_path_protect_compress;
            String dstPath;


            // Genero el nuevo path con el mismo nombre pero nueva extensión
            nameTrim = srcPath.split("\\.");
            nameTrim[(nameTrim.length)-1] = "HUF";
            dstPath = String.join(".",nameTrim);

            // Inserto solo si no voy a proteger, para no codificar la fecha
            if(!first_panel_protect) {
                DateBlock.insertDate(dstPath, stringDate, stringTime);
            }


            sizes = Huffman.Compresion.huffman(srcPath, dstPath);

            resetHuffman();

        }
        // Eligio proteger
        if(first_panel_protect){

            // Strings para paths de archivos
            String extension = "";
            String srcPath;
            String dstPath;

            // Tamaño del modulo Hamming
            int tamaño = first_panel_module_size_first;

            // Extensiones de Hamming sin error
            if(!first_panel_insertError){
                switch (first_panel_module_size_first){
                    case 32: extension = "HA1"; break;
                    case 128: extension = "HA2"; break;
                    case 1024: extension = "HA3"; break;
                    case 4096: extension = "HA4"; break;
                    case 16384: extension = "HA5"; break;
                }
            }
            // Extensiones de Hamming con error
            else{
                switch (first_panel_module_size_first){
                    case 32: extension = "HE1"; break;
                    case 128: extension = "HE2"; break;
                    case 1024: extension = "HE3"; break;
                    case 4096: extension = "HE4"; break;
                    case 16384: extension = "HE5"; break;
                }
            }

            // Genero el nuevo path con el mismo nombre pero nueva extensión
            nameTrim = first_panel_path_protect_compress.split("\\.");
            nameTrim[(nameTrim.length)-1] = extension;
            dstPath = String.join(".",nameTrim);

            // Si comprime y protege entonces el path de entrada tiene otra extensión a la ingresada (HUF)
            if(first_panel_compress){
                // Abro el archivo comprimido
                nameTrim = first_panel_path_protect_compress.split("\\.");
                nameTrim[(nameTrim.length)-1] = "HUF";
                srcPath = String.join(".",nameTrim);


            }
            else{
                // Abro el archivo original
                srcPath = first_panel_path_protect_compress;
            }


            DateBlock.insertDate(dstPath,stringDate,stringTime);

            // Codifico
            sizes = Hamming.Codificacion.codificar(srcPath,dstPath,tamaño,first_panel_insertError);



            resetHamming();
        }


        // Retorno para el cartel de alerta
        if(!first_panel_protect && !first_panel_compress){
            return new ResultType("Debe seleccionarse alguna opción", sizes[0], sizes[1], true);
        }
        else{
            return new ResultType("¡Operación exitosa!",sizes[0], sizes[1], false);
        }

    }


    public ResultType second_panel_action(){

        String[] nameTrim;
        String newExtension = "";
        double[] sizes = new double[2];

        String dateTime;

        boolean readDate = false;
        boolean dateError = false;


        // Elige desproteger
        if(second_panel_unprotect){

            // Strings para path de archivos
            String extension;
            String srcPath = second_panel_path_unprotect_uncompress;
            String dstPath;

            // Tamaño del módulo Hamming
            int tamaño = 0;

            nameTrim = srcPath.split("\\.");
            extension = nameTrim[(nameTrim.length)-1];

            // Dependiendo el número en la extension es el tamaño del módulo
            switch(extension.charAt(2)){
                case '1': tamaño = 32; break;
                case '2': tamaño = 128; break;
                case '3': tamaño = 1024; break;
                case '4': tamaño = 4096; break;
                case '5': tamaño = 16384; break;
            }

            // Dependiendo si la entrada tiene o no errores y si se corrigen.
            if(extension.charAt(1)== 'E' && !second_panel_toCorrect){
                switch (tamaño){
                    case 32: newExtension = "DE1"; break;
                    case 128: newExtension = "DE2"; break;
                    case 1024: newExtension = "DE3"; break;
                    case 4096: newExtension = "DE4"; break;
                    case 16384: newExtension = "DE5"; break;
                }
            }
            else{
                switch (tamaño){
                    case 32: newExtension = "DH1"; break;
                    case 128: newExtension = "DH2"; break;
                    case 1024: newExtension = "DH3"; break;
                    case 4096: newExtension = "DH4"; break;
                    case 16384: newExtension = "DH5"; break;
                }
            }


            nameTrim = srcPath.split("\\.");
            nameTrim[(nameTrim.length)-1] = newExtension;
            dstPath = String.join(".",nameTrim);

            dateTime = DateBlock.extractDate(srcPath);

            try {
                Date date = datetimeFormat.parse(dateTime);

                if(date.before(new Date())){

                    sizes = Hamming.Decodificacion.decodificar(srcPath,dstPath,tamaño,second_panel_toCorrect,true);
                    resetHamming();
                    readDate = true;

                }
                else{
                    dateError = true;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        // Elige descomprimir
        if(second_panel_decompress){

            String srcPath;
            String dstPath;

            // Genero el nuevo path con el mismo nombre pero nueva extensión
            nameTrim = second_panel_path_unprotect_uncompress.split("\\.");
            nameTrim[(nameTrim.length)-1] = "DHU";
            dstPath = String.join(".",nameTrim);

            // Si antes protegio cambio la extension

            if(second_panel_unprotect){
                nameTrim = first_panel_path_protect_compress.split("\\.");
                nameTrim[(nameTrim.length)-1] = newExtension;
                srcPath = String.join(".",nameTrim);
            }
            else{
                srcPath = second_panel_path_unprotect_uncompress;
            }

            if(!readDate){

                dateTime = DateBlock.extractDate(srcPath);

                try {
                    Date date = datetimeFormat.parse(dateTime);

                    if(date.before(new Date())){

                        sizes = Huffman.Descompresion.dehuffman(srcPath, dstPath, true);
                        resetHuffman();
                        readDate = true;

                    }
                    else{
                        dateError = true;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else{
                sizes = Huffman.Descompresion.dehuffman(srcPath, dstPath, false);
                resetHuffman();
            }
        }

        // Retorno para el cartel de alerta
        if(!second_panel_unprotect && !second_panel_decompress){
            return new ResultType("Debe seleccionarse alguna opción", sizes[0], sizes[1], true);
        }
        if(dateError){
            return new ResultType("El archivo no puede descomprimirse/descompactarse porque la fecha configurada es posterior a la actual", sizes[0], sizes[1], dateError);
        }
        else{
            return new ResultType("¡Operación exitosa!",sizes[0], sizes[1], dateError);
        }

    }


    public static void resetHamming(){

        Hamming.EscrituraArchivo.setByteActual((byte) 0);
        Hamming.EscrituraArchivo.setByteCompleto((byte) 0);
        Hamming.EscrituraArchivo.setCantActual((byte) 0 );
        Hamming.EscrituraArchivo.setCantCompleto((byte) 0);
    }

    public static void resetHuffman(){

        Huffman.Compresion.setTablaCod(new HashMap<>() );
        Huffman.Descompresion.setTablaDecod(new HashMap<>());
    }


}
