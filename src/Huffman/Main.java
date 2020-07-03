package Huffman;

public class Main {

    public static void main(String args[]){

        String dir1 = "ejemplo_grande.txt";
        String dir2 = "huffman.txt";
        String dir3 = "dehuffman.txt";

        /*
        File file1 = new File(dir1);
        File file2 = new File(dir2);
        File file3 = new File(dir3);
        */



        /*
        FileReader fr2 = new FileReader(file2);
        BufferedReader huffman = new BufferedReader(fr2);

        FileReader fr3 = new FileReader(file3);
        BufferedReader dehuffman = new BufferedReader(fr3);
        */
        Compresion.huffman(dir1,dir2);
        Descompresion.dehuffman(dir2,dir3);



    }
}
