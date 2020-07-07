package Huffman;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;

public class LecturaArchivo {

    static private int chars = 0;

    // Lee caracteres para codificar
    public static char[] leerChars(BufferedReader br){

        char[] module = new char[200];

        // Lee un módulo de 200 caracteres para codificar.
        try {
            setChars(br.read(module,0,200));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo leer el archivo");
        }

        return module;

    }

    public static int getChars() {
        return chars;
    }

    public static void setChars(int chars) {
        LecturaArchivo.chars = chars;
    }

    // Lee bytes para decodificar el contenido
    public static ModuloCod leerBytes(BufferedInputStream br){

        byte[] module = new byte[200];
        int chars = 0;
        try{
            chars = br.read(module,0,200);
        }
        catch (IOException e){
            System.out.println("No se pudo leer el archivo.");
        }
        return new ModuloCod(module, chars);
    }

    // Lee bytes para decodificar la tabla
    public static ModuloCod leerTabla(BufferedInputStream br){

        byte read = 0;
        byte aux = 0;
        boolean newLine1 = false;
        boolean newLine2 = false;
        byte[] module = new byte[600];
        int cant = 0;
        try{
            cant = br.read(module,0,600);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModuloCod(module,cant);
    }
}
