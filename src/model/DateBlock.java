package model;

import java.io.*;

public class DateBlock {

    // TODO testear!
    public static void insertDate(String dst, String stringDate, String stringTime){

        byte[] codificacion = new byte[5];
        int index = 0;
        // Para copiar bytes
        byte mascByteExt = 1;
        byte mascByteCopy = 1;
        byte auxByte = 0;

        // Para copiar shorts
        short mascShort = 1;
        short auxShort = 0;

        String[] trim = stringDate.split("/");
        byte day = Byte.parseByte(trim[0]);
        byte month = Byte.parseByte(trim[1]);
        short year = Short.parseShort(trim[2]);

        trim = stringTime.split(":");
        byte hour = Byte.parseByte(trim[0]);
        byte minute = Byte.parseByte(trim[1]);

        try {
            // Abro archivo
            File fileW = new File(dst);
            fileW.createNewFile();
            FileOutputStream fw = new FileOutputStream(fileW);
            BufferedOutputStream bw = new BufferedOutputStream(fw);

            // Copio el día
            mascByteExt = (byte) (mascByteExt << 4);
            mascByteCopy = (byte) (mascByteCopy << 7);

            // Los 3 bytes para la fecha

            for(int i = 0; i<5; i++){

                auxByte = (byte) (day & mascByteExt);

                if(auxByte != 0){
                    codificacion[index] = (byte) (codificacion[index] | mascByteCopy);
                }

                mascByteCopy = (byte) ((mascByteCopy  & 0xff) >>> 1);
                mascByteExt = (byte) ((mascByteExt  & 0xff) >>> 1);

            }

            mascByteExt = 1;
            mascByteExt = (byte) (mascByteExt << 3);

            // Copio el mes
            for(int i = 0; i<4; i++){

                auxByte = (byte) (month & mascByteExt);

                if(auxByte != 0){
                    codificacion[index] = (byte) (codificacion[index] | mascByteCopy);
                }

                mascByteCopy = (byte) ((mascByteCopy  & 0xff) >>> 1);
                mascByteExt = (byte) ((mascByteExt  & 0xff) >>> 1);

                if(mascByteCopy == 0 ){
                    mascByteCopy = 1;
                    mascByteCopy = (byte) (mascByteCopy << 7);
                    index++;
                }

            }

            mascShort = (short) (mascShort << 10);

            // Copio el año
            for(int i = 0; i<11; i++){

                auxShort = (short) (year & mascShort);

                if(auxShort != 0){

                    codificacion[index] = (byte) (codificacion[index] | mascByteCopy);
                }

                mascShort = (short) (mascShort >>> 1);
                mascByteCopy = (byte) ((mascByteCopy  & 0xff) >>> 1);

                if(mascByteCopy == 0 ){
                    mascByteCopy = 1;
                    mascByteCopy = (byte) (mascByteCopy << 7);
                    index++;
                }

            }

            // Los 2 bytes para la hora
            index = 3;

            mascByteExt = (byte) (mascByteExt << 3);

            // Cargo la hora
            for(int i = 0; i<4; i++){

                auxByte = (byte) (hour & mascByteExt);

                if(auxByte != 0){
                    codificacion[index] = (byte) (codificacion[index] | mascByteCopy);
                }

                mascByteCopy = (byte) ((mascByteCopy  & 0xff) >>> 1);
                mascByteExt = (byte) ((mascByteExt  & 0xff) >>> 1);

                if(mascByteCopy == 0 ){
                    mascByteCopy = 1;
                    mascByteCopy = (byte) (mascByteCopy << 7);
                    index++;
                }

            }

            mascByteExt = (byte) (mascByteExt << 4);
            // Caro los minutos
            for(int i = 0; i<5; i++){

                auxByte = (byte) (minute & mascByteExt);

                if(auxByte != 0){
                    codificacion[index] = (byte) (codificacion[index] | mascByteCopy);
                }

                mascByteCopy = (byte) ((mascByteCopy  & 0xff) >>> 1);
                mascByteExt = (byte) ((mascByteExt  & 0xff) >>> 1);

            }


            bw.write(codificacion);
            bw.close();

        }
        catch (IOException e){
            System.out.println("Error al abrir archivo");
        }

    }
}
