package Bank;

import java.sql.*;

public class User {
    private String user_name;
    private String user_pin;
    private double balance;

    public User(String user_name, String user_pin) {
        this.user_name = user_name;
        this.user_pin = user_pin;
    }

    public boolean register() {
        String sql = "INSERT INTO users(user_name, user_pin) VALUES(?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_name);
            pstmt.setString(2, user_pin);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
        return false;
    }

    public boolean login() {
        String sql = "SELECT balance FROM users WHERE user_name = ? AND user_pin = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user_name);
            pstmt.setString(2, user_pin);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                balance = rs.getDouble("balance");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount for deposit.");
            return false;
        }
        String sql = "UPDATE users SET balance = balance + ? WHERE user_name = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setString(2, user_name);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                balance += amount;
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Deposit failed: " + e.getMessage());
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount for withdrawal.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return false;
        }
        String sql = "UPDATE users SET balance = balance - ? WHERE user_name = ?";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setString(2, user_name);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                balance -= amount;
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
        return false;
    }
}