package org.chukwuma.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationServiceConcurrently {

    public static CompletableFuture<Boolean> isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return CompletableFuture.supplyAsync(() -> false);
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        return CompletableFuture.supplyAsync(matcher::matches);
    }

    public static CompletableFuture<Boolean> isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return CompletableFuture.supplyAsync(() -> false);
        }
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        Pattern pattern = Pattern.compile(passwordRegex);

        Matcher matcher = pattern.matcher(password);

        return CompletableFuture.supplyAsync(matcher::matches);
    }

    public static CompletableFuture<Boolean> isUsernameValid(String username) {
        return CompletableFuture.supplyAsync(() -> username != null && username.length() >= 4);
    }

    public static CompletableFuture<Boolean> isValidDateOfBirth(String dateOfBirth) {
        try {
            LocalDate dob = LocalDate.parse(dateOfBirth);

            LocalDate currentDate = LocalDate.now();

            LocalDate minDateOfBirth = currentDate.minusYears(16);

            return CompletableFuture.supplyAsync(() -> !dateOfBirth.isEmpty() && dob.isBefore(minDateOfBirth));
        } catch (DateTimeParseException e) {
            return CompletableFuture.supplyAsync(() -> false);
        }
    }

    public static String validateUserConcurrently(User user) {

        final String[] message = {"false -> "};
        if (user == null)
            return "false";

        String username = user.getUsername();
        String email = user.getEmail();
        String password = user.getPassword();
        String dateOfBirth = user.getDob();
        // Concurrent validation using CompletableFuture
        CompletableFuture<Boolean> usernameValidation = isUsernameValid(username);
        CompletableFuture<Boolean> emailValidation = isValidEmail(email);
        CompletableFuture<Boolean> passwordValidation = isValidPassword(password);
        CompletableFuture<Boolean> dobValidation = isValidDateOfBirth(dateOfBirth);

        // Combine validation results
        CompletableFuture<Void> allValidations = CompletableFuture.allOf(
                usernameValidation, emailValidation, passwordValidation, dobValidation
        );

        // Wait for all validations to complete
        return allValidations.thenApply(v -> {
            if (usernameValidation.join() && emailValidation.join() && passwordValidation.join() && dobValidation.join()) {
                return JWTClass.generateJwtToken(username, email);
            }
            if (!usernameValidation.join()) {
                if (username == null || username.isEmpty()) {
                    message[0] += "username is null or empty";
                } else {
                    message[0] += "username is invalid";
                }
            }
            if (!emailValidation.join()) {
                if (email == null || email.isEmpty()) {
                    message[0] += "email is null or empty";
                } else {
                    message[0] += "email is invalid";
                }
            }
            if (!passwordValidation.join()) {
                if (password == null || password.isEmpty()) {
                    message[0] += "password is null or empty";
                } else {
                    message[0] += "password is invalid";
                }
            }
            if (!dobValidation.join()) {
                if (dateOfBirth == null || dateOfBirth.isEmpty()) {
                    message[0] += "date of birth is null or empty";
                } else {
                    message[0] += "date of birth is invalid";
                }
            }
            return message[0];
        }).join();

    }

    public static String validateToken(String token, String username) {
        boolean isTokenValid = JWTClass.validateToken(token, username);

        if (isTokenValid){
            return "verification pass";
        } else {
            return "verification fails";
        }

    }
}
