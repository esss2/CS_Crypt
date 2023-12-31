package crypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;


public class crypt {

    public crypt(){}
    public SecretKey generateOrLoadKey(String keyFilePath, String inputFile) throws IOException, NoSuchAlgorithmException {
        File keyFile = new File(keyFilePath);

        if (keyFile.exists()) {
            // Si el archivo de clave ya existe, intentamos encontrar la clave correspondiente
            BufferedReader keyReader = new BufferedReader(new FileReader(keyFile));
            String line;
            while ((line = keyReader.readLine()) != null) {
                String[] parts = line.split("#");
                if (parts.length == 2 && parts[0].equals(inputFile)) {
                    // Encontramos una clave que coincide con el archivo de entrada
                    byte[] encodedKey = Base64.getDecoder().decode(parts[1]);
                    keyReader.close();
                    return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
                }
            }
            keyReader.close();
        }

        // Si no encontramos una clave correspondiente, generamos una nueva clave
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();

        // Convierte la SecretKey en una cadena Base64
        String secretKeyString = inputFile + "#" + Base64.getEncoder().encodeToString(secretKey.getEncoded());

        // Guarda la clave en el archivo
        FileWriter archivoEscritura = null;
        try {
            archivoEscritura = new FileWriter(keyFilePath, true);
            archivoEscritura.write(secretKeyString + "\n");
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
        SecretKey secretKey=generateOrLoadKey(keyFilePath,inputFileName);
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

