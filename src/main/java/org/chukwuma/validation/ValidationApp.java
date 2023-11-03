package org.chukwuma.validation;

import java.util.Scanner;

public class ValidationApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User();

        System.out.print("Please input your username: ");
        String username = scanner.nextLine();
        user.setUsername(username);
        System.out.print("Please input your email: ");
        String email = scanner.nextLine();
        user.setEmail(email);
        System.out.print("Please input your password: ");
        String password = scanner.nextLine();
        user.setPassword(password);
        System.out.print("Please input your date of birth (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();
        user.setDob(dateOfBirth);

        String message = ValidationService.validateUser(user);
        System.out.println("the validation returned: " + message);

        String concurrentValidationMessage = ValidationServiceConcurrently.validateUserConcurrently(user);
        System.out.println("the concurrent validation returned: " + concurrentValidationMessage);
        scanner.close();
    }
}