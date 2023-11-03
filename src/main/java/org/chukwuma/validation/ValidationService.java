package org.chukwuma.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {

    public static boolean isValidEmail(String email) {
        if(email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern = Pattern.compile(emailRegex);

        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        if(password == null || password.isEmpty()) {
            return false;
        }
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        Pattern pattern = Pattern.compile(passwordRegex);

        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean isUsernameValid(String username) {
        return username != null && username.length() >= 4;
    }

    public static boolean isValidDateOfBirth(String dateOfBirth) {
        try {
            LocalDate dob = LocalDate.parse(dateOfBirth);

            LocalDate currentDate = LocalDate.now();

            LocalDate minDateOfBirth = currentDate.minusYears(16);

            return !dateOfBirth.isEmpty() && dob.isBefore(minDateOfBirth);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String validateUser(User user) {
        String message = "false -> ";
        if (user == null)
            return "false";
        if (isUsernameValid(user.getUsername()) && isValidEmail(user.getEmail())
                && isValidPassword(user.getPassword()) &&  isValidDateOfBirth(user.getDob())) {
            return "true";
        }

        if (!isUsernameValid(user.getUsername())) {
            if (user.getUsername() == null || user.getUsername().isEmpty()){
                message += "Username: not empty\n";
            } else {
                message += "Username: min 4 characters\n";
            }
        }
        if (!isValidEmail(user.getEmail())) {
            if (user.getEmail() == null || user.getEmail().isEmpty()){
                message += "Email: not empty\n";
            } else {
                message += "Email: invalid\n";
            }
        }
        if (!isValidPassword(user.getPassword())){
            if (user.getPassword() == null || user.getPassword().isEmpty()){
                message += "Password: not empty\n";
            } else {
                message += "Password: not a strong password.\n";
            }
        }
        if (!isValidDateOfBirth(user.getDob())){
            if (user.getDob() == null){
                message += "Date of birth: not empty\n";
            } else {
                message += "Date of birth: must be 16 years or older\n";
            }
        }

        return message;
    }

}
