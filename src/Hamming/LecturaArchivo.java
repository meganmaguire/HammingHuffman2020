package Hamming;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class LecturaArchivo {

    private static int intActual = 0;
    private static int cantActual = 0;
    private static byte cantBytes = 4;
    private static int bytes = 0;

    // cantBits =  cantidad de bits de información
    public static int[] leerBits(BufferedInputStream fb, short cantBits) {
        int j = 0;
        short completeBytes = 0;
        int maskExtract = 1;
        int maskCopy = 1, aux=0;
        int result = 0;
        int[] bitsCargados;
        boolean carga = false;

        maskExtract = maskExtract<<31;
        maskCopy = maskCopy<<31;

        // Cuantos ints necesito en el arreglo
        completeBytes = (short)Math.ceil(cantBits/32.0);
        // Tamaño del arreglo con los bit de información según el tamaño del módulo
        bitsCargados = new int[completeBytes];

        // TODO se puede refactorizar el for.
        for (int i =0 ; i<cantBits; i++){

            // Si me quedé sin bits recargo
            if (cantActual <= 0) {
                LecturaArchivo.leerBytes(fb);
            }
            // Si completé un entero lo agrego
            if( i!=0 && ((i%32) == 0)){
                bitsCargados[j] = result;
                result = 0;
                maskCopy = 1;
                maskCopy = maskCopy << 31;
                carga = true;
                j++;
            }

            aux = intActual & maskExtract;
            if (aux != 0) {
                result = maskCopy ^ result;
            }
            maskCopy = maskCopy >>> 1;
            intActual = intActual << 1;
            cantActual--;

        }
        bitsCargados[j] = result;
        return bitsCargados;

    }

    public static int leerBytes(BufferedInputStream fb){

        byte[] module = new byte[4];

        // Lee 4 bytes del archivo correspondiente. Avanza el cursor.
        // Los bytes leídos se almacenan en module, un arreglo de bytes.
        try {
            setBytes(fb.read(module,0,cantBytes));
            // Convierto array de 4 bytes en int
            ByteBuffer wrapped = ByteBuffer.wrap(module);
            intActual = wrapped.getInt();
            cantActual = getBytes() *8;
        } catch (IOException e) {
            e.printStackTrace();
            intActual = 0;
            cantActual = 0;
        }
        return getBytes();
    }

    public static int getBytes() {
        return bytes;
    }

    public static void setBytes(int bytes) {
        LecturaArchivo.bytes = bytes;
    }
}
