package decrypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;

public class placeholderDecrypt {

    public static void desencriptar(String claveBase64) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] claveEnBytes = Base64.getDecoder().decode(claveBase64);
        SecretKey secretKey = new SecretKeySpec(claveEnBytes, "AES"); // "AES" es el algoritmo que se usará, puedes cambiarlo según tus necesidades

        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        FileInputStream inputStream = new FileInputStream("C:\\Users\\josec\\IdeaProjects\\CS_Crypt\\src\\crypt\\adios.txt");
        FileOutputStream outputStream = new FileOutputStream("C:\\Users\\josec\\IdeaProjects\\CS_Crypt\\DecryptedFiles\\quetal.txt");

        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        cipherInputStream.close();
        outputStream.close();

        System.out.println("Imagen desencriptada exitosamente.");
    }

    public static void main(String[] args) throws Exception {
        String claveBase64 = "SYqZtJjcIYapU+oAd2WCcQ=="; // Clave en formato Base64
        String textoEncriptado = "UBpW4xGpoXQpMwy079TZhOsNlk7TX4lQiO+UTmybvkHAD8A5bX0C+ooNNX6VzH9TiC6xzAsToD5QFcowmYZydg";

        try {
            desencriptar(claveBase64);
            System.out.println("Texto encriptado: " + textoEncriptado);
            //System.out.println("Texto desencriptado: " + textoDesencriptado);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
