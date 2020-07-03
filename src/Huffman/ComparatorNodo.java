package Huffman;

import java.util.Comparator;

public class ComparatorNodo implements Comparator<Nodo> {


    @Override
    public int compare(Nodo n1, Nodo n2) {
        return n1.getFrecuencia() - n2.getFrecuencia();
    }
}
