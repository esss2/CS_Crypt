package decrypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.io.*;

public class decrypt {

    public decrypt(){}

    public void desencriptar(String claveBase64, String archivo) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] claveEnBytes = Base64.getDecoder().decode(claveBase64);
        SecretKey secretKey = new SecretKeySpec(claveEnBytes, "AES");

        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        FileInputStream inputStream = new FileInputStream("../EncryptedFiles/" + archivo);
        FileOutputStream outputStream = new FileOutputStream("../DecryptedFiles/" +archivo);

        CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        cipherInputStream.close();
        outputStream.close();

        System.out.println("Archivo desencriptado exitosamente.");
    }
}
