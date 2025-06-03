package service;

import java.security.SecureRandom;

/**
 * Serviço responsável por gerar senhas seguras aleatórias,
 * contendo letras maiúsculas, minúsculas, números e símbolos.
 */
public class PasswordGeneratorService {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%&*()_-+=<>?/{}[]|";
    private static final String ALL_CHARS = UPPERCASE + LOWERCASE + NUMBERS + SYMBOLS;

    private final SecureRandom random = new SecureRandom();

    /**
     * Gera uma senha aleatória com no mínimo 8 caracteres.
     * A senha gerada contém pelo menos uma letra maiúscula, uma minúscula,
     * um número e um símbolo.
     *
     * @param length O tamanho desejado da senha
     * @return Senha segura embaralhada
     */
    public String generate(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters.");
        }

        StringBuilder password = new StringBuilder(length);
        password.append(getRandomChar(UPPERCASE));
        password.append(getRandomChar(LOWERCASE));
        password.append(getRandomChar(NUMBERS));
        password.append(getRandomChar(SYMBOLS));

        for (int i = 4; i < length; i++) {
            password.append(getRandomChar(ALL_CHARS));
        }

        return shuffle(password.toString());
    }

    /**
     * Retorna um caractere aleatório da string informada.
     *
     * @param chars Conjunto de caracteres disponíveis
     * @return Um caractere aleatório
     */
    private char getRandomChar(String chars) {
        int index = random.nextInt(chars.length());
        return chars.charAt(index);
    }

    /**
     * Embaralha os caracteres de uma string.
     *
     * @param input A string a ser embaralhada
     * @return String com caracteres embaralhados
     */
    private String shuffle(String input) {
        char[] array = input.toCharArray();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
        }
        return new String(array);
    }
}
