package service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.Key;

public class FileEncryptor {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    // Chave AES (16 caracteres = 128 bits)
    private static final String SECRET_KEY = "queroumemprego01"; 

    public static void encrypt(String inputFilePath, String outputFilePath) throws Exception {
        doCrypto(Cipher.ENCRYPT_MODE, inputFilePath, outputFilePath);
    }

    private static void doCrypto(int cipherMode, String inputFilePath, String outputFilePath) throws Exception {
        Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(cipherMode, secretKey);

        byte[] inputBytes = Files.readAllBytes(new File(inputFilePath).toPath());
        byte[] outputBytes = cipher.doFinal(inputBytes);

        try (FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {
            outputStream.write(outputBytes);
        }
    }

    public static void main(String[] args) {
        String input = "config.properties";
        String output = "config.enc";

        try {
            encrypt(input, output);
            System.out.println("Arquivo criptografado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
