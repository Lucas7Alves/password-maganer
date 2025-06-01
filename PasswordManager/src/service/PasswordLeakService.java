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

public class PasswordLeakService {

	public boolean checkLeakPassword(String password) {
		String sha1 = hashGenerator(password);
		return apiResponse(prefixHash(sha1), suffixHash(sha1));
	}

	private String hashGenerator(String password) {
		String sha1 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(password.getBytes(StandardCharsets.UTF_8));
			sha1 = bytesToHex(digest).toUpperCase();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return sha1;
	}

	private boolean apiResponse(String prefix, String suffix) {
		HttpClient client = HttpClient.newHttpClient();
		String apiUrl = "https://api.pwnedpasswords.com/range/" + prefix;

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).GET().build();

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
