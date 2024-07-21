package Bank;

import java.util.Scanner;

public class Bank_Manage {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Bank Management System");
        System.out.println("1. Register");
        System.out.println("2. Login");
        int choice = scanner.nextInt();

        scanner.nextLine();  // Consume newline

        if (choice == 1) {
            System.out.println("Enter username:");
            String user_name = scanner.nextLine();
            System.out.println("Enter 4-digit PIN:");
            String user_pin = scanner.nextLine();

            User user = new User(user_name, user_pin);
            if (user.register()) {
                System.out.println("Account created successfully.");
            } else {
                System.out.println("Error creating account.");
            }
        } else if (choice == 2) {
            System.out.println("Enter username:");
            String user_name = scanner.nextLine();
            System.out.println("Enter 4-digit PIN:");
            String user_pin = scanner.nextLine();

            User user = new User(user_name, user_pin);
            if (user.login()) {
                System.out.println("Login successful.");
                showUserMenu(user);
            } else {
                System.out.println("Invalid credentials.");
            }
        }
    }

    private static void showUserMenu(User user) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("Your balance is: " + user.getBalance());
            } else if (choice == 2) {
                System.out.println("Enter amount to deposit:");
                double amount = scanner.nextDouble();
                if (user.deposit(amount)) {
                    System.out.println("Deposit successful.");
                } else {
                    System.out.println("Error depositing money.");
                }
            } else if (choice == 3) {
                System.out.println("Enter amount to withdraw:");
                double amount = scanner.nextDouble();
                if (user.withdraw(amount)) {
                    System.out.println("Withdrawal successful.");
                } else {
                    System.out.println("Error withdrawing money.");
                }
            } else if (choice == 4) {
                break;
            }
        }
    }
}