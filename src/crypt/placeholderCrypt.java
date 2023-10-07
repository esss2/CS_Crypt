package crypt;

import javax.crypto.*;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.util.Base64;
public class placeholderCrypt {

    public static SecretKey generateKey(String keyFilePath,String inputFile) throws NoSuchAlgorithmException, IOException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        // Convierte la SecretKey en una cadena Base64
        String secretKeyString = inputFile+" # "+Base64.getEncoder().encodeToString(secretKey.getEncoded());

        // Guarda la clave en un archivo de texto en la ubicación especificada
        try (FileWriter keyFileWriter = new FileWriter(keyFilePath)) {
            keyFileWriter.write(secretKeyString);
        }
        return secretKey;
    }
    public static void encriptaArchivo(String inputFile, String outputFolder,String keyFilePath) throws Exception {
        // Obtiene el nombre del archivo de entrada sin la ruta
        String inputFileName = new File(inputFile).getName();
        SecretKey secretKey=generateKey(keyFilePath,inputFileName);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        FileInputStream inputStream = new FileInputStream(inputFile);



        // Genera la ruta completa del archivo de salida en la carpeta especificada
        String outputFile = outputFolder + File.separator + inputFileName + ".encrypted";

        FileOutputStream outputStream = new FileOutputStream(outputFile);

        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            cipherOutputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        cipherOutputStream.close();

        System.out.println("archivo encriptado exitosamente.\n Archivo de salida: " + outputFile);
    }
    /*public static void decryptImage(String inputFile, String outputFolder, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        FileInputStream inputStream = new FileInputStream(inputFile);


        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);

        byte[] buffer = new byte[4096];
        int bytesRead;
        String inputFileName = new File(inputFile).getName();
        String outputFile = outputFolder + File.separator + inputFileName ;
        FileOutputStream outputStream2 = new FileOutputStream(outputFile);

        while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
            outputStream2.write(buffer, 0, bytesRead);
        }

        cipherInputStream.close();
        outputStream2.close();

        System.out.println("Imagen desencriptada exitosamente.");
    }*/

    public static void main(String[] args) throws Exception {
        String keyFilePath = "C:\\Users\\mgeg2\\IdeaProjects\\CS_Crypt\\keys.txt";

        String inputFile1 ="C:\\Users\\mgeg2\\IdeaProjects\\CS_Crypt\\src\\crypt\\hola.txt"; // Reemplaza con la ruta de tu imagen de entrada
        String outputfolder1 ="C:\\Users\\mgeg2\\IdeaProjects\\CS_Crypt\\EncryptedFiles";
        encriptaArchivo(inputFile1,outputfolder1,keyFilePath);


    }
}

