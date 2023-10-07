package crypt;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;

public class placeholderCrypt {
    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }
    public static void encryptImage(String inputFile, String outputFile, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            cipherOutputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        cipherOutputStream.close();

        System.out.println("Imagen encriptada exitosamente.");
    }
    /*public static void decryptImage(String inputFile, String outputFile, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        FileInputStream inputStream = new FileInputStream(inputFile);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        cipherInputStream.close();
        outputStream.close();

        System.out.println("Imagen desencriptada exitosamente.");
    }*/

    public static void main(String[] args) throws Exception {
        SecretKey secretKey = generateKey();
        System.out.println("Generated Key: " + secretKey);
        String inputFile1 = "C:\\Users\\josec\\IdeaProjects\\CS_Crypt\\src\\crypt\\hola.txt"; // Reemplaza con la ruta de tu imagen de entrada
        String Encrypt ="C:\\Users\\josec\\IdeaProjects\\CS_Crypt\\src\\crypt\\adios.txt";  // Reemplaza con la ruta de tu imagen encriptada;
        //String Decrypt ="C:\\Users\\mgeg2\\IdeaProjects\\CS_Crypt\\src\\crypt\\qtal.txt";  // Reemplaza con la ruta de tu imagen encriptada;
        encryptImage(inputFile1,Encrypt,secretKey);
        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        //decryptImage(Encrypt,Decrypt,secretKey);

    }
}