package crypt;

import javax.crypto.*;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;


public class crypt {

    public crypt(){}

    /*public SecretKey generateKey(String keyFilePath, String inputFile) throws NoSuchAlgorithmException, IOException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        // Convierte la SecretKey en una cadena Base64
        String secretKeyString = inputFile + "#" + Base64.getEncoder().encodeToString(secretKey.getEncoded()) + "\n"; // Agregamos "\n" al final

        // Guarda la clave en un archivo de texto en la ubicación especificada
        ArrayList<String> archivosLeidos = new ArrayList<String>();
        FileReader archivoLectura = null;
        BufferedReader reader;
        try {
            //abro el archivo y leo su contenido
            archivoLectura = new FileReader(keyFilePath);
            reader = new BufferedReader(archivoLectura);
            String line = reader.readLine();
            while (line != null) {
                archivosLeidos.add(line);
                line = reader.readLine();
            }
        }catch(IOException e){
                e.printStackTrace();
        }finally {
            if(archivoLectura != null){
                archivoLectura.close();
            }
        }

        //añado al contenido la nueva linea
        archivosLeidos.add(secretKeyString);

        FileWriter archivoEscritura = null;
        try{
            archivoEscritura = new FileWriter(keyFilePath);
            for(String escribe : archivosLeidos){
                archivoEscritura.write(escribe);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(archivoEscritura != null){
                archivoEscritura.close();
            }
        }

        /*
        List<String> info = new ArrayList<String>();
        try{
            info = Files.readAllLines(Paths.get(keyFilePath));
            info.add(secretKeyString);
            Files.write(Paths.get(inputFile), info, StandardOpenOption.WRITE);
        }catch(Exception e){
            e.printStackTrace();
        }


        return secretKey;
    }*/

    public SecretKey generateKey(String keyFilePath, String inputFile) throws NoSuchAlgorithmException, IOException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        // Convierte la SecretKey en una cadena Base64
        String secretKeyString = inputFile + "#" + Base64.getEncoder().encodeToString(secretKey.getEncoded());

        // Guarda la clave en un archivo de texto en la ubicación especificada
        FileWriter archivoEscritura = null;
        try {
            archivoEscritura = new FileWriter(keyFilePath, true); // Abre el archivo en modo de apertura (append)
            archivoEscritura.write(secretKeyString + "\n"); // Agrega la clave al final del archivo
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (archivoEscritura != null) {
                archivoEscritura.close();
            }
        }
        return secretKey;
    }
    public void encriptaArchivo(String inputFile, String outputFolder,String keyFilePath) throws Exception {
        // Obtiene el nombre del archivo de entrada sin la ruta
        String inputFileName = new File(inputFile).getName();
        SecretKey secretKey=generateKey(keyFilePath,inputFileName);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        FileInputStream inputStream = new FileInputStream(inputFile);



        // Genera la ruta completa del archivo de salida en la carpeta especificada
        String outputFile = outputFolder + File.separator + inputFileName;

        FileOutputStream outputStream = new FileOutputStream(outputFile);

        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            cipherOutputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        cipherOutputStream.close();

        System.out.println("Archivo encriptado exitosamente.\nArchivo de salida: " + outputFile);
    }
}

