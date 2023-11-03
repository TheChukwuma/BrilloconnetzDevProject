package org.chukwuma.validation;

import java.security.SecureRandom;

public class JWTSecret {

    public static void main(String[] args) {
        System.out.println(generateRandomSecretKey());
    }
        public static String generateRandomSecretKey() {

            int length = 64;

            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
            StringBuilder key = new StringBuilder();

            SecureRandom random = new SecureRandom();
            for (int i = 0; i < length; i++) {
                int randomIndex = random.nextInt(characters.length());
                key.append(characters.charAt(randomIndex));
            }

            return key.toString();
        }


}
