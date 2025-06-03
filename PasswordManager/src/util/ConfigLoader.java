package util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.security.Key;
import java.util.Properties;

/**
 * Utilitário para carregar um arquivo de configurações criptografado (config.enc),
 * utilizando o algoritmo AES para descriptografia.
 */
public class ConfigLoader {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static final String SECRET_KEY = "queroumemprego01";

    private static Properties props = null;

    /**
     * Retorna as propriedades descriptografadas do arquivo config.enc.
     *
     * @return Objeto Properties com as configurações carregadas
     */
    public static synchronized Properties getProperties() {
        if (props == null) {
            props = new Properties();
            try {
                Key secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                cipher.init(Cipher.DECRYPT_MODE, secretKey);

                byte[] encryptedBytes = Files.readAllBytes(new File("config.enc").toPath());
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

                try (InputStream input = new ByteArrayInputStream(decryptedBytes)) {
                    props.load(input);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return props;
    }
}
