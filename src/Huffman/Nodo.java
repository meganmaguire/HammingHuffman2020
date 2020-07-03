package Huffman;

public class Nodo implements Comparable<Nodo>{


    private char caracter;
    private int frecuencia;
    private String codigo;
    private Nodo hijoIzq;
    private Nodo hijoDer;

    public Nodo(){
        this.caracter = 0;
        this.frecuencia = 0;
        this.codigo = "";
        this.hijoIzq = null;
        this.hijoDer = null;
    }

    public Nodo(char caracter, int frecuencia){
        this.caracter = caracter;
        this.frecuencia = frecuencia;
        this.codigo = "";
        this.hijoIzq = null;
        this.hijoDer = null;
    }

    public Nodo(char caracter, int frecuencia, Nodo hijoIzq, Nodo hijoDer){
        this(caracter,frecuencia);
        this.hijoIzq = hijoIzq;
        this.hijoDer = hijoDer;
    }

    public char getCaracter() {
        return caracter;
    }

    public void setCaracter(char caracter) {
        this.caracter = caracter;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Nodo getHijoIzq() {
        return hijoIzq;
    }

    public void setHijoIzq(Nodo hijoIzq) {
        this.hijoIzq = hijoIzq;
    }

    public Nodo getHijoDer() {
        return hijoDer;
    }

    public void setHijoDer(Nodo hijoDer) {
        this.hijoDer = hijoDer;
    }



    @Override
    public int compareTo(Nodo n) {
        return this.getFrecuencia()-n.getFrecuencia();
    }

    public String toString(){
        return "caracter: " + this.getCaracter() + "\t codigo: " + this.getCodigo();
    }

    public void printArbol(Nodo root){
        if(root != null){
            if(root.getHijoDer() != null){
                System.out.println(root.getHijoDer().toString());
            }
            if(root.getHijoIzq() != null){
                System.out.println(root.getHijoIzq().toString());
            }
            printArbol(root.getHijoDer());
            printArbol(root.getHijoIzq());

        }
    }
}
