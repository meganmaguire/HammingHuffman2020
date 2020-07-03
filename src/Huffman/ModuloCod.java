package Huffman;

public class ModuloCod {

    // Módulo con las codificaciones
    private byte[] modulo;
    // Cantidad de posiciones
    private int chars;

    public ModuloCod(){

    }

    public ModuloCod(byte[] modulo, int chars){

        this.modulo = modulo;
        this.chars = chars;
    }

    public byte[] getModulo() {
        return modulo;
    }

    public void setModulo(byte[] modulo) {
        this.modulo = modulo;
    }

    public int getChars() {
        return chars;
    }

    public void setChars(int chars) {
        this.chars = chars;
    }
}
