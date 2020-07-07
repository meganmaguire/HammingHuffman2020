package Hamming;

import java.io.*;

public class Decodificacion {

    public static double[] decodificar(String src, String dst, int tamaño, boolean error, boolean fecha){

        int[] module;
        int[] hamModule;
        double[] sizes = new double[2];
        byte[] consume = new byte[5];

        try {
            // Archivo de lectura
            File fileR = new File(src);
            FileInputStream fr = new FileInputStream(fileR);
            BufferedInputStream br = new BufferedInputStream(fr);

            // Archivo de escritura
            File fileW = new File(dst);
            fileW.createNewFile();
            FileOutputStream fw = new FileOutputStream(fileW);
            BufferedOutputStream bw = new BufferedOutputStream(fw);


            // Consumo los primeros 5 bytes
            if(fecha) {
                br.read(consume, 0, 5);
            }
            do{

                hamModule = LecturaArchivo.leerModulo(br,tamaño);

                if(error){
                    hamModule = Error.corregirError(hamModule, tamaño);
                }

                module = Decodificacion.dehamming(hamModule,tamaño);

                EscrituraArchivo.escribirBits(bw,module);
            }
            while(LecturaArchivo.getBytes() != -1);

            br.close();
            bw.close();

            // Tamaños para imprimir en bytes
            sizes[0] = fileR.length();
            sizes[1] = fileW.length();

        }
        catch (IOException e){
            System.out.println("Error en Archivo");
        }

        return sizes;

    }

    public static int[] dehamming(int[] vInfo, int tamaño){
        // Iteradores
        int c = 0;
        // Indices para los vectores de resultado y de información
        int indexRes = 0, indexInf = 0;
        // Máscaras para trabajo de bit
        int mascInfo = 1, mascCodificado = 1;

        // Variables auxiliares
        int aux = 0;
        // Vector resultado dehamminizado
        int[] vRes = new int[vInfo.length];

        // Inicializo las máscaras
        mascCodificado = mascCodificado << 31;
        mascInfo = mascInfo << 31;


        // Itero en la cantidad de bits que voy a tener de salida.
        for(int i = 0; i < tamaño; i++){
            // Si es un bit con control lo paso por alto y avanzo al siguiente bit
            if((Math.pow(2,c)-1) == i){
                c++;
            }
            // Sino, copio el bit de información al resultado
            else{
                // Extraigo el bit
                aux = vInfo[indexRes] & mascCodificado;
                // Comparo si es 0 o 1 el bit extraído
                if(aux != 0){
                    // Si es un 1, entonces lo agrego al vRes, sino ignoro porque ya es 0
                    vRes[indexInf] = vRes[indexInf] | mascInfo;
                }
                // Avanzo un bit en la máscara de info (salida)
                mascInfo = mascInfo >>> 1;
            }
            // Avanzo un bit en la máscara del codificado (entrada)
            mascCodificado = mascCodificado >>> 1;

            // Control de reseteo de máscaras
            if (mascInfo == 0){
                mascInfo = 1;
                mascInfo = mascInfo << 31;
                indexInf++;
            }
            if(mascCodificado == 0){
                mascCodificado = 1;
                mascCodificado = mascCodificado << 31;
                indexRes++;
            }
        }

        return vRes;
    }

    public static byte controlBits(int tamaño){

        switch (tamaño){
            case 32: return 6;
            case 128: return 8;
            case 2014: return 11;
            case 4096: return 13;
            case 16384: return 15;
            default: return 0;
        }
    }

}
