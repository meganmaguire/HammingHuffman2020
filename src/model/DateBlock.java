package model;

import java.io.*;

public class DateBlock {

    // Inserta una fecha codificada en 5 bytes al comienzo del archivo
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
            fileW.delete();
            fileW.createNewFile();
            FileOutputStream fw = new FileOutputStream(fileW);
            BufferedOutputStream bw = new BufferedOutputStream(fw);

            // Copio el día
            mascByteExt = (byte) (mascByteExt << 4);
            mascByteCopy = (byte) (mascByteCopy << 7);

            // Los 3 bytes para la fecha

            // Copio el dia
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

            mascByteCopy = 1;
            mascByteCopy = (byte) (mascByteCopy << 7);

            mascByteExt = 1;
            mascByteExt = (byte) (mascByteExt << 4);

            // Cargo la hora
            for(int i = 0; i<5; i++){

                auxByte = (byte) (hour & mascByteExt);

                if(auxByte != 0){
                    codificacion[index] = (byte) (codificacion[index] | mascByteCopy);
                }

                mascByteCopy = (byte) ((mascByteCopy  & 0xff) >>> 1);
                mascByteExt = (byte) ((mascByteExt  & 0xff) >>> 1);

            }

            mascByteExt = 1;
            mascByteExt = (byte) (mascByteExt << 5);
            // Caro los minutos
            for(int i = 0; i<6; i++){

                auxByte = (byte) (minute & mascByteExt);

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


            bw.write(codificacion);
            bw.close();

        }
        catch (IOException e){
            System.out.println("Error al abrir archivo");
        }

    }

    public static String extractDate(String src){

        String dateTime = "";

        byte[] codificacion = new byte[5];
        int index = 0;
        // Para copiar bytes
        byte mascByteExt = 1;
        byte mascByteCopy = 1;
        byte auxByte = 0;

        // Para copiar shorts
        short mascShort = 1;
        short auxShort = 0;

        // Datos a extraer
        byte day = 0;
        byte month = 0;
        short year = 0;
        byte hour = 0;
        byte minute = 0;

        try {
            // Archivo de lectura
            File fileR = new File(src);
            FileInputStream fr = new FileInputStream(fileR);
            BufferedInputStream br = new BufferedInputStream(fr);

            br.read(codificacion,0, 5);

            mascByteCopy = (byte) (mascByteCopy << 4);
            mascByteExt = (byte) (mascByteExt << 7);

            // Los 3 bytes para la fecha

            // Copio el dia
            for(int i = 0; i<5; i++){

                auxByte = (byte) (codificacion[index] & mascByteExt);

                if(auxByte != 0){
                    day = (byte) (day | mascByteCopy);
                }

                mascByteCopy = (byte) ((mascByteCopy  & 0xff) >>> 1);
                mascByteExt = (byte) ((mascByteExt  & 0xff) >>> 1);

            }

            mascByteCopy = 1;
            mascByteCopy = (byte) (mascByteCopy << 3);

            // Copio el mes
            for(int i = 0; i<4; i++){

                auxByte = (byte) (codificacion[index] & mascByteExt);

                if(auxByte != 0){
                    month = (byte) (month | mascByteCopy);
                }

                mascByteCopy = (byte) ((mascByteCopy  & 0xff) >>> 1);
                mascByteExt = (byte) ((mascByteExt  & 0xff) >>> 1);

                if(mascByteExt == 0){
                    mascByteExt = 1;
                    mascByteExt = (byte) (mascByteExt << 7);
                    index++;
                }
            }

            mascShort = (short) (mascShort << 10);

            for(int i = 0; i<11; i++){

                auxByte = (byte) (codificacion[index] & mascByteExt);

                if(auxByte != 0){
                    year = (short) (year | mascShort);
                }

                mascShort = (short) (mascShort >>> 1);
                mascByteExt = (byte) ((mascByteExt  & 0xff) >>> 1);

                if(mascByteExt == 0){
                    mascByteExt = 1;
                    mascByteExt = (byte) (mascByteExt << 7);
                    index++;
                }
            }

            index = 3;

            mascByteCopy = 1;
            mascByteCopy = (byte) (mascByteCopy << 4);

            mascByteExt = 1;
            mascByteExt = (byte) (mascByteExt << 7);

            // Cargo la hora
            for(int i = 0; i<5; i++){

                auxByte = (byte) (codificacion[index] & mascByteExt);

                if(auxByte != 0){
                    hour = (byte) (hour | mascByteCopy);
                }

                mascByteCopy = (byte) ((mascByteCopy  & 0xff) >>> 1);
                mascByteExt = (byte) ((mascByteExt  & 0xff) >>> 1);
            }

            mascByteCopy = 1;
            mascByteCopy = (byte) (mascByteCopy << 5);

            // Cargo los minutos
            for(int i = 0; i<6; i++){

                auxByte = (byte) (codificacion[index] & mascByteExt);

                if(auxByte != 0){
                    minute = (byte) (minute | mascByteCopy);
                }

                mascByteCopy = (byte) ((mascByteCopy  & 0xff) >>> 1);
                mascByteExt = (byte) ((mascByteExt  & 0xff) >>> 1);

                if(mascByteExt == 0){
                    mascByteExt = 1;
                    mascByteExt = (byte) (mascByteExt << 7);
                    index++;
                }
            }

            br.close();

            dateTime = day + "/" + month + "/" + year + " " + hour + ":" + minute;

        }
        catch(IOException e){
            System.out.println("Error en el archivo");
        }

        return dateTime;
    }
}
