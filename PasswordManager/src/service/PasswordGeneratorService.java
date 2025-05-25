package service;

import java.security.SecureRandom;

public class PasswordGeneratorService {

	 private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	    private static final String NUMBERS = "0123456789";
	    private static final String SYMBOLS = "!@#$%&*()_-+=<>?/{}[]|";

	    private static final String ALL_CHARS = UPPERCASE + LOWERCASE + NUMBERS + SYMBOLS;

	    private final SecureRandom random = new SecureRandom();

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

	    private char getRandomChar(String chars) {
	        int index = random.nextInt(chars.length());
	        return chars.charAt(index);
	    }

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
