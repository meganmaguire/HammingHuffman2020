package Hamming;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
/*
    ----------- ACLARACIÓN --------------

    En esta clase se trabaja con bytes por una cuestión de tamaño mínimo. Cuando leo un archivo de texto común, leo como
    mínimo un byte, incluso si posteriormente cuando se aplique Hamming tenga que rellenar con 0 porque no son suficientes,
    cuando vuelva a escribir el archivo dehamminizado voy a tener n cantidad de bytes. Si la escritura se trabajaba con,
    por ejemplo, enteros, iba a tener espacios vacíos que sobraban al fraccionar los bits de información para los módulos
    de diferentes tamaños, que iban a afectar al resultado. Aún si fuera rellenando estos espacios con bits de otros
    enteros, al final siempre tenía chance de tener un entero con 0's de más. Por ello en este caso se trabaja con bytes,
    para que el resultado sea el deseado.

*/


// TODO: Tipos de formato de salida.
public class EscrituraArchivo {

    private static byte byteActual = 0;
    private static byte byteCompleto = 0;
    private static byte cantActual = 0;
    private static byte cantCompleto = 0;

    public static void escribirInt(BufferedOutputStream fs, int[] hamModule){

        try {

            ByteBuffer bufferByte = ByteBuffer.allocate(hamModule.length * Integer.BYTES);
            IntBuffer bufferInt = bufferByte.asIntBuffer();
            bufferInt.put(hamModule);
            byte[] bytes = bufferByte.array();

            fs.write(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo escribir el fichero.");
        }


    }
    public static void escribirBytes(BufferedOutputStream fs, byte[] hamModule){

        try {
            fs.write(hamModule);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo escribir el fichero.");
        }


    }

    public static void escribirBits(BufferedOutputStream fs, int[] module){

        byte controlBits;
        byte maskExtract = 1, maskCopy = 1;
        int aux, cantBits;
        byte[] write = new byte[1];
        int moduleSize = module.length * 32;

        // Obtengo la cantidad de bits de control según el tamaño del módulo.
        controlBits = Decodificacion.controlBits(moduleSize);

        maskExtract = (byte) (maskExtract << 7);
        maskCopy = (byte) (maskCopy << 7-cantCompleto);

        // -------- PRUEBA ----------
        ByteBuffer bufferByte = ByteBuffer.allocate(module.length * Integer.BYTES);
        IntBuffer bufferInt = bufferByte.asIntBuffer();
        bufferInt.put(module);
        byte[] bytes = bufferByte.array();

        // -------- PRUEBA ----------

        for(int i = 0; i<bytes.length; i++){

            byteActual = bytes[i];
            // El último entero posee menos bits a escribir
            if((bytes.length-1) == i) {
                cantBits = 8 - controlBits;
                cantActual = (byte) (8 - controlBits);
            }
            else {
                cantBits = 8;
                cantActual = 8;
            }

            for(int j = 0; j < cantBits; j++){
                aux = byteActual & maskExtract;
                if(aux != 0){
                   byteCompleto = (byte) (byteCompleto ^ maskCopy);
                }
                maskCopy = (byte) ((maskCopy & 0xff) >>> 1);
                byteActual = (byte) (byteActual << 1);
                cantCompleto++;
                cantActual--;
                if(cantCompleto == 8){
                    write[0] = byteCompleto;
                    escribirBytes(fs,write);
                    cantCompleto = 0;
                    byteCompleto = 0;
                    maskCopy = 1;
                    maskCopy = (byte) (maskCopy << 7);
                }
            }
        }
    }

    public static byte getByteActual(){
        return byteActual;
    }

    public static byte getByteCompleto(){
        return byteCompleto;
    }

    public static void setByteActual(byte byteAc){
        byteActual = byteAc;
    }

    public static void setByteCompleto(byte byteCom){
        byteCompleto = byteCom;
    }

    public static void setCantActual(byte cant){
        cantActual = cant;
    }

    public static void setCantCompleto(byte cant){
        cantCompleto = cant;
    }


}
