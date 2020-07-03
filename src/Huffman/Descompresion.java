package Huffman;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Descompresion {

    private static Map<String,Character> tablaDecod = new HashMap<>();

    public static void dehuffman(String src, String dst){

        ModuloCod moduloCod;
        String module;

        try {

            FileInputStream fr = new FileInputStream(src);
            BufferedInputStream br = new BufferedInputStream(fr);

            FileOutputStream fw = new FileOutputStream(dst);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fw, StandardCharsets.ISO_8859_1));

            // Leo la tabla
            ModuloCod tablaMod = LecturaArchivo.leerTabla(br);

            // Armo la tabla
            generarTabla(tablaMod);

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


            br.close();
            bw.close();

        } catch (IOException e) {
            System.out.println("No se pudo abrir el archivo");
        }

    }

    // A partir de los bytes leídos del archivo armo la tabla
    public static void generarTabla(ModuloCod tablaMod){

        int i = 0;
        byte longitud;
        int cantBytes;

        char simbolo = 0;
        String codigo = "";

        char mascChar = 1;
        mascChar = (char) (mascChar << 15);

        byte mascByte = 1;
        byte aux = 0;
        mascByte = (byte) (mascByte << 7);

        byte[] module = tablaMod.getModulo();

        // Cantidad de entradas a la tabla, tengo 4 bytes por cada entrada
        int size = tablaMod.getChars();

        while(i < size){

            for(int j =0; j<16; j++){
                aux = (byte) (module[i] & mascByte);

                // Si extraigo un 1
                if(aux != 0){
                    simbolo = (char) (simbolo | mascChar);
                }

                mascChar = (char) (mascChar >>> 1);
                mascByte = (byte) ((mascByte & 0xff) >>> 1);

                if(mascByte == 0){
                    mascByte = 1;
                    mascByte = (byte) (mascByte << 7);
                    // Siguiente byte
                    i++;
                }

            }

            longitud = module[i];
            cantBytes = (int) Math.ceil(longitud/8.0);
            // Siguiente byte
            i++;

            // Preparo la máscara
            mascByte = 1;
            mascByte = (byte) (mascByte << 7);

            for(int j = 0; j<longitud; j++){

                aux = (byte) (module[i] & mascByte);

                // Si extraigo un 1 concateno un 1, sino concateno un 0
                if(aux != 0){
                    codigo += "1";
                }
                else{
                    codigo += "0";
                }

                mascByte = (byte) ((mascByte & 0xff) >>> 1);

                if(mascByte == 0){
                    mascByte = 1;
                    mascByte = (byte) (mascByte << 7);
                    // Siguiente byte
                    i++;
                }

            }

            tablaDecod.put(codigo,simbolo);

            simbolo = 0;
            codigo = "";
            if(mascByte != -128){
                i++;
            }
            // Reseteo las máscaras
            mascChar =1;
            mascChar = (char) (mascChar << 15);
            mascByte = 1;
            mascByte = (byte) (mascByte << 7);
        }
    }

    public static String decodificar(ModuloCod moduleCod){

        String module = "";
        int i;
        String codigo = "";
        byte aux = 0;


        // Mascara para leer bits
        byte mascByte = 1;
        mascByte = (byte) (mascByte << 7);

        for(i=0; i<moduleCod.getChars(); i++){

            for(int j = 0; j < 8; j++) {

                aux = (byte) (moduleCod.getModulo()[i] & mascByte);

                if (aux != 0) {
                    codigo += "1";
                } else {
                    codigo += "0";
                }

                if (tablaDecod.containsKey(codigo)) {

                    module += tablaDecod.get(codigo);
                    codigo = "";
                }

                mascByte = (byte) ((mascByte & 0xff) >>> 1);
            }
            // Reseteo máscara
            if(mascByte == 0){
                mascByte = 1;
                mascByte = (byte) (mascByte << 7);
            }

        }

        return module;
    }
}
