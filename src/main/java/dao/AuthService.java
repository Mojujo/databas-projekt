package dao;

import utility.LoggerUtil;
import utility.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthService {
    private final Connection conn;

    // SERVICE TO HASH PASSWORDS AND AUTHENTICATE LOGIN INFORMATION
    public AuthService(Connection conn) {
        this.conn = conn;
    }

    // Checks for existing account and attempts to authenticate hashed password
    public boolean authenticate(String username, String password) {
        String sql = """
                SELECT PASSWORD
                FROM EMPLOYEE
                WHERE EMAIL = ?;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String hashPass = rs.getString("PASSWORD");
                    return PasswordUtil.checkPassword(password, hashPass);
                }
            }
        } catch (SQLException e) {
            LoggerUtil.logError("Error authenticating password", e);
        }
        return false;
    }
}
