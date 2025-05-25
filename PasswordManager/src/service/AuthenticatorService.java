package service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AuthenticatorService {

    private final Map<String, TokenInfo> tokenStore = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public String generateToken(String email) {
        int token = 100000 + random.nextInt(900000);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        tokenStore.put(email, new TokenInfo(String.valueOf(token), expiresAt));
        return String.valueOf(token);
    }

    public boolean validateToken(String email, String token) {
        if (!tokenStore.containsKey(email)) return false;

        TokenInfo info = tokenStore.get(email);
        if (LocalDateTime.now().isAfter(info.expiresAt)) return false;

        return info.token.equals(token);
    }

    private static class TokenInfo {
        String token;
        LocalDateTime expiresAt;

        TokenInfo(String token, LocalDateTime expiresAt) {
            this.token = token;
            this.expiresAt = expiresAt;
        }
    }
}
