package service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.stream.Stream;

/**
 * Serviço para verificar se uma senha foi comprometida
 * usando a API "Have I Been Pwned".
 */
public class PasswordLeakService {

    /**
     * Verifica se a senha foi encontrada em vazamentos.
     *
     * @param password A senha a ser verificada
     * @return true se a senha foi vazada, false caso contrário
     */
    public boolean checkLeakPassword(String password) {
        String sha1 = hashGenerator(password);
        return apiResponse(prefixHash(sha1), suffixHash(sha1));
    }

    /**
     * Gera o hash SHA-1 da senha fornecida.
     *
     * @param password A senha original
     * @return Hash SHA-1 em letras maiúsculas
     */
    private String hashGenerator(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(digest).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Consulta a API do HIBP com o prefixo do hash.
     *
     * @param prefix Os 5 primeiros caracteres do hash
     * @param suffix O restante do hash
     * @return true se o sufixo for encontrado na resposta
     */
    private boolean apiResponse(String prefix, String suffix) {
        HttpClient client = HttpClient.newHttpClient();
        String apiUrl = "https://api.pwnedpasswords.com/range/" + prefix;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        try {
            HttpResponse<Stream<String>> response = client.send(request, HttpResponse.BodyHandlers.ofLines());

            if (response.statusCode() == 200) {
                return response.body().anyMatch(line -> line.startsWith(suffix));
            } else {
                System.err.println("Erro na requisição: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Falha na requisição: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    private String prefixHash(String hash) {
        return hash.substring(0, 5);
    }

    private String suffixHash(String hash) {
        return hash.substring(5);
    }

    private String bytesToHex(byte[] bytes) {
        return HexFormat.of().formatHex(bytes);
    }
}
