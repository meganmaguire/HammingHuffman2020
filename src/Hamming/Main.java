package Hamming;

import java.io.*;

public class Main {

    // TODO: Añadir menú por consola para primera entrega

    public static void main (String args[]){

        String dir1 = "amores peligrosos.txt";
        String dir2 = "hamming.txt";
        String dir3 = "dehamming.txt";
        int[] module;
        int[] hamModule;
        int tamaño = 4096;
        int bitsControl = Decodificacion.controlBits(tamaño);

        try {
            // Archivo de lectura
            FileInputStream file = new FileInputStream(dir1);
            BufferedInputStream fb = new BufferedInputStream(file);
            // Archivo de escritura
            FileOutputStream file2 = new FileOutputStream(dir2);
            BufferedOutputStream hamminizado = new BufferedOutputStream(file2);

            FileOutputStream file3 = new FileOutputStream(dir3);
            BufferedOutputStream dehamminizado = new BufferedOutputStream(file3);


            // reemplazar con do while y el primer read afuera.
            while(true) {
                module = LecturaArchivo.leerBits(fb, (short) (tamaño-bitsControl));
                System.out.print(Integer.toBinaryString(module[0]));
                System.out.print(Integer.toBinaryString(module[1]));
                System.out.print(Integer.toBinaryString(module[2]));
                System.out.println(Integer.toBinaryString(module[3]));
                if(isEmpty(module))
                    break;

                hamModule = Codificacion.hamming(module, (short) tamaño);
                System.out.print(Integer.toBinaryString(hamModule[0]));
                System.out.print(Integer.toBinaryString(hamModule[1]));
                System.out.print(Integer.toBinaryString(hamModule[2]));
                System.out.println(Integer.toBinaryString(hamModule[3]));

                //hamModule = Error.insertarError(hamModule,tamaño);
                System.out.print(Integer.toBinaryString(hamModule[0]));
                System.out.print(Integer.toBinaryString(hamModule[1]));
                System.out.print(Integer.toBinaryString(hamModule[2]));
                System.out.println(Integer.toBinaryString(hamModule[3]));

                //hamModule = Error.corregirError(hamModule,tamaño);
                System.out.print(Integer.toBinaryString(hamModule[0]));
                System.out.print(Integer.toBinaryString(hamModule[1]));
                System.out.print(Integer.toBinaryString(hamModule[2]));
                System.out.println(Integer.toBinaryString(hamModule[3]));

                module = Decodificacion.dehamming(hamModule,tamaño);
                System.out.print(Integer.toBinaryString(module[0]));
                System.out.print(Integer.toBinaryString(module[1]));
                System.out.print(Integer.toBinaryString(module[2]));
                System.out.println(Integer.toBinaryString(module[3]));

                EscrituraArchivo.escribirInt(hamminizado, hamModule);
                EscrituraArchivo.escribirBits(dehamminizado,module);
            }
            hamminizado.close();
            dehamminizado.close();
            fb.close();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("No se encontró el fichero.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo leer/escribir el fichero.");
        }

    }

    public static boolean isEmpty(int[] module) {
        int bytes = LecturaArchivo.getBytes();
        boolean emptyModule = true;
        for(int i=0; i<module.length; i++){
            if(module[i] != 0 )
                emptyModule = false;
        }
        if (bytes < 0 && emptyModule)
            return true;
        else
            return false;
    }
}
