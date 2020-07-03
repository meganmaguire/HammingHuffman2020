package Huffman;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class EscrituraArchivo {


    // Escribe bytes codificados
    public static void escribirBytes(BufferedOutputStream bw, ModuloCod codModule){
        try{
            // Me quedo solo con las posiciones que me interesan del arreglo, descarto las vacías.
            byte[] trimModule = new byte[codModule.getChars()];

            for(int i = 0; i<codModule.getChars(); i++){
                trimModule[i] = codModule.getModulo()[i];
            }

            bw.write(trimModule);
        }
        catch (IOException e){
            System.out.println("No se pudo escribir el archivo.");
        }

    }

    // Escribe caracteres decodificados
    public static void escribirChar(BufferedWriter bw, String module){

        try{
            bw.write(module);
        }
        catch(IOException e){
            System.out.println("No se pudo escribir el archivo.");
        }
    }

}
