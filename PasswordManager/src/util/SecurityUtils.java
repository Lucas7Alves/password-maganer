package util;

import java.util.regex.Pattern;

public class SecurityUtils {

    // Padrões de validação
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");
    private static final Pattern TOKEN_PATTERN = Pattern.compile("^[0-9]{6}$");
    private static final int MIN_PASSWORD_LENGTH = 8;

    /**
     * Sanitiza uma string removendo tags HTML/XML e espaços extras
     * @param input A string a ser sanitizada
     * @return String sanitizada
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("<[^>]*>", "").trim();
    }

    /**
     * Sanitiza um token, removendo caracteres não numéricos
     * @param token O token a ser sanitizado
     * @return Token sanitizado
     * @throws IllegalArgumentException Se o token for nulo
     */
    public static String sanitizeToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Token não pode ser nulo");
        }
        return token.replaceAll("[^0-9]", "");
    }

    /**
     * Valida campos obrigatórios
     * @param fields Campos a serem validados
     * @throws IllegalArgumentException Se algum campo estiver vazio
     */
    public static void validateRequiredFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                throw new IllegalArgumentException("Todos os campos são obrigatórios.");
            }
        }
    }

    /**
     * Valida o formato de e-mail
     * @param email E-mail a ser validado
     * @throws IllegalArgumentException Se o e-mail for inválido
     */
    public static void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }
    }

    /**
     * Valida o formato de nome
     * @param name Nome a ser validado
     * @throws IllegalArgumentException Se o nome for inválido
     */
    public static void validateName(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("Nome contém caracteres inválidos.");
        }
        if (name.length() < 2 || name.length() > 100) {
            throw new IllegalArgumentException("Nome deve ter entre 2 e 100 caracteres.");
        }
    }

    /**
     * Valida a força da senha
     * @param password Senha a ser validada
     * @throws IllegalArgumentException Se a senha não atender aos requisitos
     */
    public static void validatePassword(String password) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("A senha deve ter no mínimo " + MIN_PASSWORD_LENGTH + " caracteres.");
        }
        if (!password.matches(".*\\d.*") || !password.matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("A senha deve conter letras e números.");
        }
    }

    /**
     * Valida o formato do token
     * @param token Token a ser validado
     * @throws IllegalArgumentException Se o token for inválido
     */
    public static void validateToken(String token) {
        if (token.isEmpty()) {
            throw new IllegalArgumentException("O campo token é obrigatório.");
        }
        if (!TOKEN_PATTERN.matcher(token).matches()) {
            throw new IllegalArgumentException("Formato de token inválido. Deve ser um código de 6 dígitos.");
        }
    }
}