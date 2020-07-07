package Huffman;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.lang.Math;

public class Compresion {

    // Tabla de frecuencia de símbolos
    private static Map<Character,Integer> tablaFreq = new HashMap<>();
    private static Map<Character,String> tablaCod = new HashMap<>();


    public static double[] huffman(String src, String dst){

        char[] module;
        ModuloCod codModule;
        int chars = 0;
        double[] sizes = new double[2];

        String[] trim = dst.split("\\.");
        trim[trim.length - 1] = "THU";
        String tablePath = String.join(".",trim);

        try {
            // Archivo de lectura
            File fileR = new File(src);
            FileInputStream fr1 = new FileInputStream(fileR);
            BufferedReader br = new BufferedReader(new InputStreamReader(fr1, StandardCharsets.ISO_8859_1));

            // Archivo de escritura
            File fileW = new File(dst);
            fileW.createNewFile();
            FileOutputStream fw2 = new FileOutputStream(fileW);
            BufferedOutputStream bw = new BufferedOutputStream(fw2);

            // Archivo de tabla
            File fileT = new File(tablePath);
            fileT.createNewFile();
            FileOutputStream ft = new FileOutputStream(fileT);
            BufferedOutputStream bt = new BufferedOutputStream(ft);

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
            generarCodificacion();

            // Escribo codificación en el txt
            escribirTabla(bt);

            // Cierro y vuelvo abrir archivo para leer desde el comienzo
            br.close();
            fr1 = new FileInputStream(src);
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

            bw.close();
            bt.close();
            br.close();

            sizes[0] = fileR.length();
            sizes[1] = fileW.length();

        }
        catch (IOException e){
            System.out.println("No se pudo abrir el archivo");
        }

        return sizes;
    }

    // Genera la tabla de frecuencia
    public static void tablaFrecuencia(char[] module,int chars){

        int i;
        Character[] objectModule = Compresion.toObject(module,chars);

        for(i=0; i<chars; i++){

            if(tablaFreq.putIfAbsent(objectModule[i],1) != null){
                tablaFreq.replace(objectModule[i], tablaFreq.get(objectModule[i])+1);
            }
        }

    }

    // En base a la tablaFreq de frecuencia obtenida se generan los códigos para cada caracter
    public static void generarCodificacion(){

        Nodo root = null, nodo1, nodo2, hijoIzq, hijoDer;


        // Creo la parva de mínimos de Huffman
        PriorityQueue<Nodo> arbolHuffman = new PriorityQueue<>(tablaFreq.entrySet().size(), new ComparatorNodo());

        // La cargo con las entradas de la tabla
        for(Map.Entry<Character,Integer> e : tablaFreq.entrySet()){
            arbolHuffman.add(new Nodo(e.getKey(),e.getValue()));
        }

        // Comienzo a armar el árbol
        while(root == null){
            nodo1 = arbolHuffman.poll();
            nodo2 = arbolHuffman.poll();

            // Si los dos nodos tienen valores válidos, entonces creo el nuevo nodo con estos como hijos
            if(nodo1 != null && nodo2 != null){
                if(nodo1.compareTo(nodo2)>=0){
                    hijoIzq = nodo2;
                    hijoDer = nodo1;
                }
                else{
                    hijoIzq = nodo1;
                    hijoDer = nodo2;
                }
                // TODO revisar la parva porque ordena extraño (pasar a max y de nuevo a min?)
                // Creo el nodo padre con los nodos como hijos, con la suma de las frecuencias y lo añado a la cola
                arbolHuffman.add(new Nodo('*',nodo1.getFrecuencia()+nodo2.getFrecuencia(),hijoIzq,hijoDer));
            }
            else{
                // Si nodo1 tiene un valor y nodo2 es nulo, significa que llegamos a la raíz del árbol
                if(nodo1 != null && nodo2 == null){
                    root = nodo1;
                }
                // Si ambos son nulos, la parva esta vacía y salgo.
                else{
                    System.out.println("Parva vacía");
                    break;
                }
            }

        }


        // Genero la codificación.
        posorden(root, "");

    }


    public static void escribirTabla(BufferedOutputStream bw){

        char simbolo;
        String codigo;
        int longMaxima = (int) Math.ceil(longitudMaxima()/8.0);
        int cantBytes = 0;

        char mascSimbolo = 1;
        mascSimbolo = (char) (mascSimbolo << 15);

        byte mascCopy = 1;
        mascCopy = (byte) (mascCopy << 7);

        char auxChar = 0;
        byte auxByte = 0;

        // El tamaño son 2 byte por entrada + n byte de codificación
        // + 1 byte para tamaño de codigo, por ello es el tamaño de la tabla multiplcado por 3 + longMaxima
        int size = tablaFreq.size()*(3+longMaxima);
        byte[] tablaModule = new byte[size];
        int indexModule = 0;



        // Itero cada elemento de la tabla con codificaciones
        for (Map.Entry<Character, String> entrada : tablaCod.entrySet()) {

            simbolo = entrada.getKey();
            codigo = entrada.getValue();

            // Cargo el simbolo, 16 bits
            for(int i = 0; i< 16; i++) {
                auxChar = (char) (simbolo & mascSimbolo);

                if (auxChar != 0) {
                    // Si extraje un 1 lo copio
                    tablaModule[indexModule] = (byte) (tablaModule[indexModule] | mascCopy);
                }

                mascSimbolo = (char) (mascSimbolo >>> 1);
                mascCopy = (byte) ((mascCopy & 0xff) >>> 1);

                if(mascCopy == 0){
                    mascCopy = 1;
                    mascCopy = (byte) (mascCopy << 7);
                    indexModule++;
                }
            }

            // Cargo el largo de la codificación
            tablaModule[indexModule] = (byte) codigo.length();
            cantBytes = (int) Math.ceil(codigo.length()/8.0);
            indexModule++;

            // Cargo la codificación
            mascCopy = 1;
            mascCopy = (byte) (mascCopy << 7);

            for(int i = 0; i<codigo.length(); i++){

                if(codigo.charAt(i) == '1'){
                    tablaModule[indexModule] = (byte) (tablaModule[indexModule] | mascCopy);
                }
                mascCopy = (byte) ((mascCopy & 0xff) >>> 1);

                if(mascCopy == 0){
                    mascCopy = 1;
                    mascCopy = (byte) (mascCopy << 7);
                    indexModule++;
                }
            }

            if(mascCopy != -128){
                indexModule++;
            }

            mascSimbolo = 1;
            mascSimbolo = (char) (mascSimbolo << 15);

            mascCopy = 1;
            mascCopy = (byte) (mascCopy << 7);

        }
        // Escribo el módulo en el archivo.
        try {

            bw.write(tablaModule,0,indexModule);
        } catch (IOException e) {

            System.out.println("No se pudo escribir el archivo.");
        }


    }

    // En base a la codificación obtenida codifica el módulo de caracteres
    public static ModuloCod codificar(char[] module, int chars){

        int i, codIndex = 0;

        // Mascara para copiado
        byte mascaraCopy = 1;
        mascaraCopy = (byte) (mascaraCopy << 7);

        byte[] codModule = new byte[chars*2];

        String codigo;

        for(i=0; i<chars; i++){
            // Busco el código del caracter en la tabla
            codigo = tablaCod.get(module[i]);

            for(int j=0; j<codigo.length(); j++){

                // Si hay un 1 en la codificación lo agrego, sino no porque ya hay un 0.
                if(codigo.charAt(j) == '1'){
                    codModule[codIndex] = (byte)(codModule[codIndex] | mascaraCopy);
                }
                // Avanzo la máscara
                mascaraCopy = (byte) ((mascaraCopy & 0xff) >>> 1);


                // Reseteo cuando agoté la máscara
                if(mascaraCopy == 0){
                    mascaraCopy = 1;
                    mascaraCopy = (byte) (mascaraCopy << 7);
                    // Próximo char del módulo de salida.
                    codIndex++;

                }
            }

        }
        // Retorno un objeto con el módulo y la cantidad de posiciones reales del módulo.
        return (new ModuloCod(codModule,codIndex+1));

    }


    public static Character[] toObject(char[] module, int chars){

        Character[] objects = new Character[chars];
        for(int i = 0; i<chars; i++){
            objects[i] = new Character(module[i]);
        }

        return objects;
    }


    public static int longitudMaxima(){

        int maximo = 0;
        for(Map.Entry<Character,String> entrada : tablaCod.entrySet()){

            if(entrada.getValue().length() > maximo){

                maximo = entrada.getValue().length();
            }
        }

        System.out.println("Longitud maxima: " + maximo);
        return maximo;
    }


    // Me genera la codificación para cada nodo hoja del árbol
    public static void posorden(Nodo root, String codigo){
        if(root.getHijoIzq() == null && root.getHijoDer() == null){
            root.setCodigo(codigo);
            tablaCod.put(root.getCaracter(),codigo);
        }
        else {
            posorden(root.getHijoDer(), codigo + "0");
            posorden(root.getHijoIzq(), codigo + "1");

        }

    }

    public static void setTablaFreq(Map<Character,Integer> tabla){
        tablaFreq = tabla;
    }

    public static void setTablaCod(Map<Character,String> tabla){
        tablaCod = tabla;
    }
}
