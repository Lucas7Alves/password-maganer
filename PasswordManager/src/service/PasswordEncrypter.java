package service;

import java.security.MessageDigest;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Classe utilitária para criptografar e descriptografar senhas com AES.
 */
public class PasswordEncrypter {
    private static final String SECRET_KEY = "malditoeohomemqueconfianohomem";
    private static SecretKeySpec secretKey;

    // Bloco estático para inicialização da chave secreta AES
    static {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = sha.digest(SECRET_KEY.getBytes("UTF-8"));
            byte[] key16 = new byte[16];
            System.arraycopy(key, 0, key16, 0, 16);
            secretKey = new SecretKeySpec(key16, "AES");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inicializar chave secreta AES", e);
        }
    }

    /**
     * Criptografa uma string usando AES.
     * @param strToEncrypt Texto a ser criptografado
     * @return Texto criptografado em Base64
     */
    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Descriptografa uma string criptografada com AES.
     * @param strToDecrypt Texto criptografado em Base64
     * @return Texto original descriptografado
     */
    public static String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(strToDecrypt);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
